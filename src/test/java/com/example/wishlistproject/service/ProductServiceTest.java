package com.example.wishlistproject.service;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void testAddProduct_shouldCallSaveOnce() {
        Product product = new Product();
        productService.addProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProductById_shouldReturnCorrectProduct() {
        Product mockProduct = new Product();
        mockProduct.setProductId(1L);
        when(productRepository.findById(1L)).thenReturn(mockProduct);

        Product result = productService.getProductById(1L);
        assertEquals(1L, result.getProductId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductsByWishlistId_shouldReturnList() {
        when(productRepository.findByWishlistId(5L)).thenReturn(List.of(new Product(), new Product()));

        List<Product> result = productService.getProductsByWishlistId(5L);
        assertEquals(2, result.size());
        verify(productRepository).findByWishlistId(5L);
    }

    @Test
    void testUpdateProduct_shouldSetIdAndCallUpdate() {
        Product product = new Product();
        productService.updateProduct(10L, product);
        assertEquals(10L, product.getProductId());
        verify(productRepository).update(product);
    }

    @Test
    void testDeleteProduct_shouldCallDeleteById() {
        productService.deleteProduct(88L);
        verify(productRepository).deleteById(88L);
    }
}