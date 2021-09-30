package com.exercise.recentlyviewedprod.repositories;

import com.exercise.recentlyviewedprod.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findTop5ByOrderByProductName();
}
