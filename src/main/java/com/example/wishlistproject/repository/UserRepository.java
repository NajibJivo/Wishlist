package com.example.wishlistproject.repository;

import com.example.wishlistproject.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(User user) {

        String sql = "INSERT INTO user (name, email, password) VALUE (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rs -> {
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        });
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, rs->{
           if(rs.next()) {
               User user = new User();
               user.setUserId(rs.getLong("user_id"));
               user.setName(rs.getString("name"));
               user.setEmail(rs.getString("email"));
               user.setPassword(rs.getString("password"));
               return Optional.of(user);
           } else {
               return Optional.empty();
           }
        });
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum)-> {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        });
        // JDBC DONE DEAL!!!
    }
}