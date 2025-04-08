package com.example.wishlistproject.service;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Tilføj et produkt
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    // Hent produkt via ID
    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Hent produkter baseret på wishlistId
    public List<Product> getProductsByWishlistId(Long wishlistId) {
        return productRepository.findByWishlistId(wishlistId);
    }

    // Opdater et produkt
    public void updateProduct(Long id, Product product) {
        product.setProductId(id);
        productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}