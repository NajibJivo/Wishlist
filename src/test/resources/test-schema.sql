DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;

CREATE TABLE users (
                       user_id bigint PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist (
                          wishlist_id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          image_url VARCHAR(255),
                          user_id bigint
);

CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE,
    wishlist_id BIGINT,
    wish_url VARCHAR(1000),
    purchase_url VARCHAR(1000),
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
    );
