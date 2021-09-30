package com.exercise.recentlyviewedprod.service;

import com.exercise.recentlyviewedprod.models.Customer;
import com.exercise.recentlyviewedprod.models.Product;
import com.exercise.recentlyviewedprod.models.ViewedProduct;
import com.exercise.recentlyviewedprod.repositories.CustomerRepository;
import com.exercise.recentlyviewedprod.repositories.ProductRepository;
import com.exercise.recentlyviewedprod.repositories.ViewedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustProdHistoryService {

    private static final int VIEWED_PRODUCTS_LIMIT = 100;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ViewedProductRepository viewedProductRepository;

    /**
     * Return list of viewed products for customer with customerId
     * or a default list in case there are no viewed list.
     * @param customerId
     * @param datesToView
     * @return
     */
    public Set<Product> getViewedProductsByCustomer(Integer customerId, Integer datesToView) {
        Customer customer = customerRepository.findById(customerId).get();
        LocalDate today = LocalDate.now();
        LocalDate range = today.minusDays(datesToView);

        List<ViewedProduct> viewedProducts = customer.getViewedProducts().stream()
                .sorted(Comparator.comparing(ViewedProduct::getViewedDate))
                .filter(viewedProduct -> viewedProduct.getViewedDate().isAfter(range))
                .collect(Collectors.toList());

        Set<Product> products = new HashSet<>();
        for (ViewedProduct viewedProduct:viewedProducts) {
            products.add(productRepository.findById(viewedProduct.getProductId()).get());
        }

        //if there is no product viewed then return default product list
        if (products.isEmpty()) {
            products = productRepository.findTop5ByOrderByProductName();
        }

        return products;//return the list of viewed products
    }

    /**
     * Whe a product is viewed by customer this operation is called to
     * register that the product was viewed for products history
     * @param customerId
     * @param productId
     */
    public void insertViewProduct(Integer customerId, Integer productId) {
        Customer customer = customerRepository.findById(customerId).get();//check if customer is null

        // if viewed list is 100 elements then remove the oldest one to insert new viewed
        if (customer.getViewedProducts().size() == VIEWED_PRODUCTS_LIMIT) {
            List<ViewedProduct> viewedProducts = customer.getViewedProducts().stream()
                    .sorted(Comparator.comparing(ViewedProduct::getViewedDate))
                    .collect(Collectors.toList());
            viewedProducts.remove(viewedProducts.size() - 1);
        }

        //Every insert for Customer with Id equals to 3 will simulate that viewed a product
        //in previous days, so we can test retrieve products for X days
        LocalDate dateViewed = LocalDate.now();
        if (customerId == 3) {
            dateViewed = dateViewed.minusDays(5);
        }

        if (!isProductAlreadyViewed(customer, productId)) {
            ViewedProduct viewedProduct = new ViewedProduct(customerId, productId, dateViewed);
            viewedProductRepository.save(viewedProduct);
            customer.getViewedProducts().add(viewedProduct);
            customerRepository.save(customer);
        }
    }

    /**
     * Delete a product viewed by customer with customerId of the list of
     * viewed products.
     * @param customerId
     * @param productId
     */
    public void deleteProduct(Integer customerId, Integer productId) {
        Customer customer = customerRepository.findById(customerId).get();
        for (ViewedProduct product : customer.getViewedProducts()) {
            if (productId == product.getProductId()) {
                customer.getViewedProducts().remove(product);
                viewedProductRepository.delete(product);
                customerRepository.save(customer);
                break;
            }
        }
    }

    /**
     * Update a specific viewed product for a specific customer with the latest date viewed
     * @param customerId
     * @param productId
     */
    public void updateViewedProduct(Integer customerId, Integer productId) {
        Customer customer = customerRepository.findById(customerId).get();
        for (ViewedProduct product : customer.getViewedProducts()) {
            if (productId == product.getProductId()) {
                product.setViewedDate(LocalDate.now());
                customerRepository.save(customer);
                break;
            }
        }
    }

    private boolean isProductAlreadyViewed(Customer customer, Integer productId) {
        List<ViewedProduct> viewedProducts = customer.getViewedProducts();
        for (ViewedProduct viewedProduct:viewedProducts) {
            if (productId == viewedProduct.getProductId()) {
                return true;
            }
        }
        return false;
    }
}
