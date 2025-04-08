package com.example.wishlistproject.model;

public class Product {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private Long wishlistId;

    public Product() {
        // Tom default constructor
    }

    public Product(String name, String description, double price, Long wishlistId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.wishlistId = wishlistId;

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }
}

