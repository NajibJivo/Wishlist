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

    @InjectMocks // Instantierer service automatisk
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiserer mocks
    }

    @Test
    void testCreateWishlist_shouldCallRepositorySave() {
        Wishlist wishlist = new Wishlist();
        wishlist.setName("Ferie");
        wishlist.setUserId(1L);

        wishlistService.createWishlist(wishlist);

        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    void testGetAllWishlists_shouldReturnAllWishlists(){
        List<Wishlist> mockList = List.of(new Wishlist(), new Wishlist());
        when(wishlistRepository.findAll()).thenReturn(mockList);

        List<Wishlist> result = wishlistService.getAllWishLists();

        assertEquals(2, result.size());
        verify(wishlistRepository).findAll();
    }


    @Test
    void testGetWishlistForUser_shouldReturnWishlistForGivenUser(){
        Long userId = 42L;
        List<Wishlist> mockList = List.of(new Wishlist());
        when(wishlistRepository.findByUserId(userId)).thenReturn(mockList);

        List<Wishlist> result = wishlistService.getWishlistsForUser(userId);

        assertEquals(1, result.size());
        verify(wishlistRepository).findByUserId(userId);
    }

    @Test
    void testGetWishlistById_shouldReturnCorrectWishlist(){
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(7L);

        when(wishlistRepository.findById(7L)).thenReturn(wishlist);

        Wishlist result = wishlistService.getWishlistById(7L);

        assertNotNull(result);
        assertEquals(7L, result.getWishlistId());

        verify(wishlistRepository).findById(7L);
    }

    @Test
    void testUpdateWishlist_shouldCallRepositoryUpdate() {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(3L);
        wishlist.setName("Opdateret");

        wishlistService.updateWishlist(3L, wishlist);

        verify(wishlistRepository).update(wishlist);
    }

    @Test
    void deleteWishlist_shouldCallRepositoryDeleteById() {
        wishlistService.deleteWishlist(5L);

        verify(wishlistRepository).deleteById(5L);
    }
}