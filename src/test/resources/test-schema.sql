DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist (
                          wishlist_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          image_url VARCHAR(255),
                          user_id BIGINT,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE product (
                         product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         description TEXT,
                         price DOUBLE,
                         wishlist_id BIGINT,
                         wish_url VARCHAR(1000),
                         purchase_url VARCHAR(1000),
                         FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
);
