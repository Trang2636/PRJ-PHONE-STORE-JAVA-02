package com.store.model;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private double total;
    private Date createdAt;

    public Order() {
    }

    public Order(int id, int userId, double total, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Order(int userId, double total) {
        this.userId = userId;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}