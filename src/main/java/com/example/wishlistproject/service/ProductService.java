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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {
        product.setId(id);
        productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}