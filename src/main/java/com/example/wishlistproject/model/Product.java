package com.example.wishlistproject.model;

public class Product {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private Long wishlistId;
    private String wishUrl;
    private String purchaseUrl;

    public Product() {
        // Tom default constructor
    }

    public Product(String name, String description, double price, Long wishlistId, String wishUrl, String purchaseUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.wishlistId = wishlistId;
        this.wishUrl = wishUrl;
        this.purchaseUrl = purchaseUrl;

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

    public String getWishUrl() {
        return wishUrl;
    }

    public void setWishUrl(String wishUrl){
        this.wishUrl = wishUrl;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }
}

