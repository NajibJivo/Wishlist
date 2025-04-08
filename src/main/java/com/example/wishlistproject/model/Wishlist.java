package com.example.wishlistproject.model;

public class Wishlist {
    private Long wishlistId;
    private String name;
    private String description;
    private String imageUrl;
    private Long userId;

    public Wishlist() {
    }

    public Wishlist(Long wishlistId, String name, String description, String imageUrl, Long userId) {
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public Wishlist(String name, String description, String imageUrl, Long userId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
