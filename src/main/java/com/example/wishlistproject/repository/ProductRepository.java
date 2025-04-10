package com.example.wishlistproject.repository;

import com.example.wishlistproject.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map result set to Product object
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setProductId(rs.getLong("product_id")); // 'id' is product's primary key
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setWishlistId(rs.getLong("wishlist_id"));
        product.setWishUrl(rs.getString("wish_url"));
        product.setPurchaseUrl(rs.getString("purchase_url")); // ✅
        return product;
    };

    // Create
    public int save(Product product) {
        String sql = "INSERT INTO product (name, description, price, wishlist_id, wish_url, purchase_url)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getDescription(),
                product.getPrice(), product.getWishlistId(), product.getWishUrl(), product.getPurchaseUrl());
    }

    // Read
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
    }

    // Update
    public int update(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, wishlist_id = ?, wish_url = ?, " +
                "purchase_url = ? WHERE product_id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getDescription(),
                product.getPrice(), product.getWishlistId(), product.getWishUrl(),
                product.getPurchaseUrl(), product.getProductId()); // ✅
    }

    // Delete
    public int deleteById(Long id) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Returnerer alle produkter for en given ønskeliste (baseret på wishlistId).
     */
    public List<Product> findByWishlistId(Long wishlistId) {
        String sql = "SELECT * FROM product WHERE wishlist_id = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, productRowMapper);
    }
}
