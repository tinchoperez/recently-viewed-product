package com.exercise.recentlyviewedprod.repositories;

import com.exercise.recentlyviewedprod.models.ViewedProduct;
import org.springframework.data.repository.CrudRepository;

public interface ViewedProductRepository extends CrudRepository<ViewedProduct, Integer> {

}
