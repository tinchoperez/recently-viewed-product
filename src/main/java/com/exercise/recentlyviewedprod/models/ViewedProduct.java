package com.exercise.recentlyviewedprod.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ViewedProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="VIEWPROD_SEQ")
    @SequenceGenerator(name="VIEWPROD_SEQ", sequenceName="VIEWPROD_SEQ", allocationSize=1)
    private int id;

    private int customerId;

    private int productId;

    private LocalDateTime viewedDate;

    public ViewedProduct() {
    }

    public ViewedProduct(int customerId, int productId, LocalDateTime viewedDate) {
        this.customerId = customerId;
        this.productId = productId;
        this.viewedDate = viewedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getViewedDate() {
        return viewedDate;
    }

    public void setViewedDate(LocalDateTime viewedDate) {
        this.viewedDate = viewedDate;
    }
}
