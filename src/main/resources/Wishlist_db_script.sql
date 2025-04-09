-- 1. Opret database (hvis den ikke findes)
CREATE DATABASE IF NOT EXISTS wishlist_db;
USE wishlist_db;

-- 2. Opret Users-tabellen
CREATE TABLE IF NOT EXISTS Users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
    );

-- 3. Opret Wishlist-tabellen
CREATE TABLE IF NOT EXISTS Wishlist (
    wishlist_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(512),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (wishlist_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
    );

-- 4. Opret Product-tabellen (One-to-Many: Wishlist → Product)
CREATE TABLE IF NOT EXISTS Products (
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    wish_url VARCHAR(1000),
    purchase_url VARCHAR(1000),
    wishlist_id BIGINT,
    PRIMARY KEY (product_id),
    FOREIGN KEY (wishlist_id) REFERENCES Wishlist(wishlist_id) ON DELETE CASCADE
    );

-- 5. Opret Reservation-tabellen (valgfri – hvis brugere kan reservere produkter)
CREATE TABLE IF NOT EXISTS Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    wishlist_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    reservation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Reserved', 'Bought', 'Canceled') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (wishlist_id) REFERENCES Wishlist(wishlist_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
    );


