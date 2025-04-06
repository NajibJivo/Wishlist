package com.example.wishlistproject.service;

import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiserer mocks
        wishlistService = new WishlistService(wishlistRepository, null); // userRepository ikke brugt
    }

    @Test
    void createWishlist_shouldCallRepositorySave() {
        Wishlist wishlist = new Wishlist("Ferie", "Sol og strand", 1L);
        wishlistService.createWishlist(wishlist);

        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    void getAllWishlist_shouldReturnAllWishlists(){

        // Arrange
        List<Wishlist> mockList = List.of(new Wishlist(), new Wishlist());
        when(wishlistRepository.findAll()).thenReturn(mockList);

        // Act
        List<Wishlist> result = wishlistService.getAllWishList();

        // Assert
        assertEquals(2, result.size());
        verify(wishlistRepository, times(1)).findAll();
    }

    @Test
    void getWishlistsForUser_shouldReturnWishlistForGivenUser() {

        // Arrange
        Long userId = 1L;
        List<Wishlist> mockList = List.of( new Wishlist());
        when(wishlistRepository.findByUserId(userId)).thenReturn(mockList);

        // Act
        List<Wishlist> result = wishlistService.getWishlistsForUser(userId);

        // Assert
        assertEquals(1, result.size());
        verify(wishlistRepository, times(1)).findByUserId(userId);
    }

}