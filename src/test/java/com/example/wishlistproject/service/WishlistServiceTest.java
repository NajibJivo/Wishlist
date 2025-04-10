package com.example.wishlistproject.service;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.model.Wishlist;
import com.example.wishlistproject.repository.ProductRepository;
import com.example.wishlistproject.repository.UserRepository;
import com.example.wishlistproject.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    private  WishlistRepository wishlistRepo;
    private ProductRepository productRepo;
    private WishlistService wishlistService;



    @BeforeEach
    void setUp() {
        wishlistRepo= mock(WishlistRepository.class);
        productRepo = mock(ProductRepository.class);
        wishlistService = new WishlistService(wishlistRepo, mock(UserRepository.class), productRepo);
    }

    @Test
    void testCreateWishlist_shouldSavedWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setDescription("Test beskrivelse");

        wishlistService.createWishlist(wishlist);

        verify(wishlistRepo, times(1)).save(wishlist);
    }

    @Test
    void testGetWishlistForUser_shouldReturnList() {
        Long userId = 1L;
        when(wishlistRepo.findByUserId(userId)).thenReturn(List.of(new Wishlist()));

        List<Wishlist> result = wishlistService.getWishlistsForUser(userId);

        assertEquals(1, result.size());
        verify(wishlistRepo, times(1)).findByUserId(userId);
    }

    @Test
    void deleteWishlist_callsRepositoryWithCorrectId() {
        Long wishlistId = 42L;

        wishlistService.deleteWishlist(wishlistId);

        verify(wishlistRepo).deleteById(wishlistId);
    }

    @Test
    void getProductsForWishlist_shouldReturnProductList() {
        Long wishlistId = 1L;
        when(productRepo.findByWishlistId(wishlistId)).thenReturn(List.of(new Product()));

        var result = wishlistService.getProductsForWishlist(wishlistId);

        assertEquals(1, result.size());
        verify(productRepo).findByWishlistId(wishlistId);
    }

    @Test
    void getAllWishLists_shouldReturnAll() {
        when(wishlistRepo.findAll()).thenReturn(List.of(new Wishlist(), new Wishlist()));

        var result = wishlistService.getAllWishLists();

        assertEquals(2, result.size());
        verify(wishlistRepo).findAll();
    }

    @Test
    void updateWishlist_shouldCallRepo() {
        Wishlist updated = new Wishlist();
        updated.setWishlistId(10L);
        updated.setName("Opdateret");

        wishlistService.updateWishlist(10L, updated);

        verify(wishlistRepo).update(updated);
    }

    @Test
    void getWishlistById_shouldReturnWishlist() {
        Wishlist wish = new Wishlist();
        wish.setWishlistId(7L);
        when(wishlistRepo.findById(7L)).thenReturn(wish);

        Wishlist result = wishlistService.getWishlistById(7L);

        assertNotNull(result);
        assertEquals(7L, result.getWishlistId());
        verify(wishlistRepo).findById(7L);
    }
}