package com.exercise.recentlyviewedprod.repositories;

import com.exercise.recentlyviewedprod.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Set<Product> findTop5ByOrderByProductName();
}
