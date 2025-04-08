package com.example.wishlistproject.repository;

import com.example.wishlistproject.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Wishlist wish = new Wishlist();
            wish.setWishlistId(rs.getLong("wishlist_id"));
            wish.setName(rs.getString("name"));
            wish.setDescription(rs.getString("description"));
            wish.setImageUrl(rs.getString("image_url"));
            wish.setUserId(rs.getLong("user_id"));
            return wish;
        });
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
