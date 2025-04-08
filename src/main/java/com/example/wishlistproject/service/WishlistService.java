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

    public void createWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getAllWishLists() {
        return wishlistRepository.findAll();
    }

    public List<Wishlist> getWishlistsForUser(Long userId){
        return wishlistRepository.findByUserId(userId);
    }

    public void updateWishlist(Long id, Wishlist updatedWishlist) {
        // Vi behøver ikke bruge 'id' direkte her, da updatedWishlist allerede har id sat
        wishlistRepository.update(updatedWishlist);
    }

    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id);
    }
}
