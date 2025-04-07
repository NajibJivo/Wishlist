package com.example.wishlistproject.model;


public class Wishlist {
    private Long wishlistId;

    private String name;
    private String description;
    private String imageUrl;
    private Long userId;
    private User user;


    public Wishlist(){
    }

    public Wishlist(Long wishlistId, String name, String description,String imageUrl, Long userId, User user) {
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.user = user;
    }

    public Wishlist(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public Wishlist(int wishlistId, String Ønskeseddel, String Ønsker, int ønskerId) {
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
