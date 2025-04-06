package com.example.wishlistproject.model;

import java.util.List;


public class User {

    private Long userId;
    private String email;
    private String name;
    private String password;

    // En bruger kan have flere ønskesedler

    private List<Wishlist> wishlists;

    public User(int i, String mail, String ayan, String hashedPassword1) {
    }

    public User(Long userId, String email, String name, String password) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists){
        this.wishlists = wishlists;
    }
}
