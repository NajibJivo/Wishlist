package com.example.wishlistproject.service;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiserer mocks
        productService = new ProductService(productRepository);
    }

    @Test
    void testAddProduct_shouldCallRepositorySave() {
        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("High-end laptop");
        product.setPrice(999.99);
        product.setWishlistId(1L);  // Brug Long for wishlistId

        productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProductById_shouldReturnProduct() {
        Long productId = 1L;  // Brug Long for produkt-id
        Product product = new Product();
        product.setProductId(productId);
        product.setName("Laptop");

        when(productRepository.findById(productId)).thenReturn(product);

        Product result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        verify(productRepository).findById(productId);
    }

    @Test
    void testGetProductsByWishlistId_shouldReturnProducts() {
        Long wishlistId = 1L;  // Brug Long for wishlistId
        List<Product> mockProducts = List.of(new Product(), new Product());
        when(productRepository.findByWishlistId(wishlistId)).thenReturn(mockProducts);

        List<Product> result = productService.getProductsByWishlistId(wishlistId);

        assertEquals(2, result.size());
        verify(productRepository).findByWishlistId(wishlistId);
    }

    @Test
    void testUpdateProduct_shouldCallRepositoryUpdate() {
        Long productId = 1L;  // Brug 'Long' for produkt-id
        Product product = new Product();
        product.setProductId(productId);
        product.setName("Updated Product");

        productService.updateProduct(productId, product);

        verify(productRepository).update(product);
    }

    @Test
    void testDeleteProduct_shouldCallRepositoryDeleteById() {
        Long productId = 1L;  //  Long for produkt-id

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }
}