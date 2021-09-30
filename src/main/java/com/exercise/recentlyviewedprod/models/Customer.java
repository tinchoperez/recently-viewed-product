package com.exercise.recentlyviewedprod.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
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
