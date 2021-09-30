package com.exercise.recentlyviewedprod.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String productName;

    private String productDescription;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

}
