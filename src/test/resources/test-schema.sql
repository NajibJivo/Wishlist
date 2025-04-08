DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS users;

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
