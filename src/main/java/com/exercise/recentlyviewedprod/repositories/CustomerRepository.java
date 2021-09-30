package com.exercise.recentlyviewedprod.repositories;

import com.exercise.recentlyviewedprod.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
