package com.store.model;

public class Product {
    private int id;
    private String name;
    private String color;
    private double price;
    private int stock;
    private int categoryId;

    public Product() {}

    public Product(int id, String name, String color, double price, int stock, int categoryId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public Product(int id, String name, double price, int stock, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() {
        return String.format("%-5d | %-20s | %-10s | %-10.2f | %-10d | %-5d",
                id, name, color, price, stock, categoryId);
    }
}