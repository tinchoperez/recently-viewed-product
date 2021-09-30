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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
    public List<Product> getViewedProductsByCustomer(Integer customerId, Integer datesToView) {
        Customer customer = customerRepository.findById(customerId).get();
        LocalDate today = LocalDate.now();
        LocalDate range = today.minusDays(datesToView);

        List<ViewedProduct> viewedProducts = customer.getViewedProducts().stream()
                .sorted(Comparator.comparing(ViewedProduct::getViewedDate))
                .filter(viewedProduct -> viewedProduct.getViewedDate().isAfter(range))
                .collect(Collectors.toList());

        List<Product> products = new LinkedList<>();
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

        ViewedProduct viewedProduct = new ViewedProduct(customerId, productId, LocalDate.now());
        viewedProductRepository.save(viewedProduct);
        customer.getViewedProducts().add(viewedProduct);
        customerRepository.save(customer);
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
            if (productId == product.getId()) {
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
            if (productId == product.getId()) {
                product.setViewedDate(LocalDate.now());
                customerRepository.save(customer);
                break;
            }
        }
    }

}
