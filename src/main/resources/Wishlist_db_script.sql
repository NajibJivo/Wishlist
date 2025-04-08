-- 1. Opret database (hvis den ikke findes)
CREATE DATABASE IF NOT EXISTS wishlist_db;
USE wishlist_db;

-- 2. Opret User-tabellen
CREATE TABLE IF NOT EXISTS Users (
    user_id bigint NOT NULL AUTO_INCREMENT, -- Ensure this matches FK references
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);

-- 3. Opret Wishlist-tabellen
CREATE TABLE IF NOT EXISTS Wishlist (
    wishlist_id bigint NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(512),
    user_id bigint NOT NULL,
    PRIMARY KEY (wishlist_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- 4. Opret Product-tabellen
CREATE TABLE IF NOT EXISTS Product (
    product_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    link VARCHAR(500),
    PRIMARY KEY (product_id)
);

-- 5. Opret Wishlist_Product (Many-to-Many relation mellem Wishlist og Product)
CREATE TABLE IF NOT EXISTS Wishlist_Product (
    wishlist_id bigint NOT NULL,
    product_id INT NOT NULL,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (wishlist_id, product_id),
    FOREIGN KEY (wishlist_id) REFERENCES Wishlist(wishlist_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE
);

-- 6. Opret Reservation-tabellen (Brugere kan reservere produkter fra ønskesedler)
CREATE TABLE IF NOT EXISTS Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint NOT NULL,  -- Hvem reserverer?
    wishlist_id bigint NOT NULL,
    product_id INT NOT NULL,
    reservation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Reserved', 'Bought', 'Canceled') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (wishlist_id) REFERENCES Wishlist(wishlist_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE,
    wishlist_id BIGINT,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
    );

