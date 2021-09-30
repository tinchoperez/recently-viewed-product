package com.exercise.recentlyviewedprod.controllers;

import com.exercise.recentlyviewedprod.models.Product;
import com.exercise.recentlyviewedprod.service.CustProdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/rvp")
public class CustProdHistoryController {

    @Autowired
    private CustProdHistoryService service;

    @GetMapping("/viewedProducts/{customerId}/{datesToView}")
    public Set<Product> getViewedProductsByCustomer(@PathVariable Integer customerId, @PathVariable Integer datesToView) {
        return service.getViewedProductsByCustomer(customerId, datesToView);
    }

    @PostMapping("/saveProduct/{customerId}/{productId}")
    public void insertViewProduct(@PathVariable Integer customerId, @PathVariable Integer productId) {
        service.insertViewProduct(customerId, productId);
    }

    @DeleteMapping("/deleteProduct/{customerId}/{productId}")
    public void deleteViewedProduct(@PathVariable Integer customerId, @PathVariable Integer productId) {
        service.deleteProduct(customerId, productId);
    }

    @PutMapping("updateProduct/{customerId}/{productId}")
    public void updateViewedProduct(@PathVariable Integer customerId, @PathVariable Integer productId) {
        service.updateViewedProduct(customerId, productId);
    }

}
