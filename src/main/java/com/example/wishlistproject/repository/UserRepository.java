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


    public Optional<User> save(User user) {
        String sql = "INSERT INTO Users (name, email, password) VALUE (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
        Long lastId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class); /** Hent det sidst indsatte ID **/
        return findById(lastId); /** Returner den oprettede bruger **/
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
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
        String sql = "SELECT * FROM Users WHERE email = ?";
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
        String sql = "SELECT * FROM Users";
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