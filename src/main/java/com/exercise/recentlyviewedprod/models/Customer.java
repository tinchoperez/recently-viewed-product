package com.exercise.recentlyviewedprod.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Customer {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    @OneToMany
    private List<ViewedProduct> viewedProducts;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ViewedProduct> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(List<ViewedProduct> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }
}
