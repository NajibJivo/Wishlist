CREATE TABLE wishlist (
                          wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          description VARCHAR(255),
                          image_url VARCHAR(512),
                          user_id BIGINT
);

CREATE TABLE user (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(255),
                      password VARCHAR(255)
);
