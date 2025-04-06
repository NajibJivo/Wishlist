package com.example.wishlistproject.service;
import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.repository.UserRepository;
import com.example.wishlistproject.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;

    }

    public void createWishlist(String name, String description, Long userId) {
        wishlistRepository.save(name, description, userId);
    }

    public List<Wishlist> getAllWishList() {
        return wishlistRepository.findAll();
    }

    public List<Wishlist> getWishlistsForUser(Long userId){
        return wishlistRepository.findByUserId(userId);
    }


}
