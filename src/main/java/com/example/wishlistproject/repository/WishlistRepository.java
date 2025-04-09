package com.example.wishlistproject.repository;

import com.example.wishlistproject.model.Product;
import com.example.wishlistproject.model.Wishlist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistRepository  {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create
    public void save(Wishlist  wishlist) {
        String sql = "INSERT INTO wishlist (name, description, image_url, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wishlist.getName(),
                wishlist.getDescription(),
                wishlist.getImageUrl(),
                wishlist.getUserId());
    }

    // Read
    public Wishlist findById(Long id) {
        // Step 1: Fetch Wishlist details
        String wishlistSql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        Wishlist wishlist;
        try {
             wishlist = jdbcTemplate.queryForObject(wishlistSql, new Object[]{id}, (rs, rowNum) -> {
                Wishlist wish = new Wishlist();
                wish.setWishlistId(rs.getLong("wishlist_id"));
                wish.setName(rs.getString("name"));
                wish.setDescription(rs.getString("description"));
                wish.setImageUrl(rs.getString("image_url"));
                wish.setUserId(rs.getLong("user_id"));
                wish.setProducts(new ArrayList<>()); // Initialize the list
                return wish;
            });
        } catch (EmptyResultDataAccessException e) {
            // Handle case where wishlist is not found if necessary, e.g., return null or throw exception
            return null; // Or throw a custom not found exception
        }


        // Step 2: Fetch associated Products
        String productsSql = "SELECT * FROM products WHERE wishlist_id = ?"; // Assuming table name is 'products'
        List<Product> products = jdbcTemplate.query(productsSql, new Object[]{id}, new ProductRowMapper()); // Use a RowMapper

        // Step 3: Add products to the wishlist
        if (wishlist != null) {
            wishlist.setProducts(products);
        }

        return wishlist;
    }

    // Define a RowMapper for Product (adjust column names as needed)
    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getLong("product_id")); // Assuming column name
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price")); // Assuming column name and type
            product.setWishUrl(rs.getString("wish_url"));
            product.setWishlistId(rs.getLong("wishlist_id")); // Assuming FK column name
            // TODO: Map purchase_url if needed
             // product.setPurchaseUrl(rs.getString("purchase_url"));
            return product;
        }
    }

    // Update
    public void update(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET name = ?, description = ?, image_url = ? WHERE wishlist_id = ?";
        jdbcTemplate.update(sql,
                wishlist.getName(),
                wishlist.getDescription(),
                wishlist.getImageUrl(),
                wishlist.getWishlistId());
    }

    // Delete
    public void deleteById(Long id){
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Wishlist> findAll() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql,(rs, rowNum)-> {
            Wishlist wish = new Wishlist();
            wish.setWishlistId(rs.getLong("wishlist_id"));
            wish.setName(rs.getString("name"));
            wish.setDescription(rs.getString("description"));
            wish.setImageUrl(rs.getString("image_url"));
            wish.setUserId(rs.getLong("user_id"));
            return wish;
        });
    }

    public void deleteAll() {
        String sql = "DELETE FROM wishlist";
        jdbcTemplate.update(sql);
    }

    public List<Wishlist> findByUserId(Long userId){
        String sqlQuery = "SELECT * FROM wishlist WHERE user_id = ?"; /** ? er placeholder for userId . **/
        return jdbcTemplate.query(sqlQuery, new Object[] {userId}, (rs, rowNum) -> {
            Wishlist wish = new Wishlist();
            wish.setWishlistId(rs.getLong("wishlist_id"));
            wish.setName(rs.getString("name"));
            wish.setDescription(rs.getString("description"));
            wish.setImageUrl(rs.getString("image_url"));
            wish.setUserId(rs.getLong("user_id"));
            return wish;
        });
    }
}
