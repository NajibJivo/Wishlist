package com.example.wishlistproject.model;

public class Product {
    private int productId;
    private double price;
    private String name;
    private String description;

    private Long wishlistId;

    public Product() {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public Product(int productId, String description, String product, double price, String link, Long wishlistId) {
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(Long id) {
    }

    public Object getId() {
        return null;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }
}

