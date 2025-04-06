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

    public void save(String name, String description, Long userId) {
        String sql = "INSERT INTO wishlist (name, description, userId) VALUES (?,?,?)";
        jdbcTemplate.update(sql,name,description, userId);
    }

    public List<Wishlist> findAll() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql,(rs, rowNum)-> {
            Wishlist w = new Wishlist();
            w.setWishlistId(rs.getLong("wishlist_id"));
            w.setName(rs.getString("name"));
            w.setDescription(rs.getString("description"));
            w.setUserId(rs.getLong("user_Id"));
            return w;
        });
    }

    public List<Wishlist> findByUserId(Long userId){
        String sqlQuery = "SELECT * FROM wishlist WHERE userId = ?"; /** ? er placeholder for userId . **/
        return jdbcTemplate.query(sqlQuery, new Object[] {userId}, (rs, rowNum) -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setWishlistId(rs.getLong("wishlist_id"));
            wishlist.setName(rs.getString("name"));
            wishlist.setDescription(rs.getString("description"));
            wishlist.setUserId(rs.getLong("userId"));
            return wishlist;
        });
    }

}
