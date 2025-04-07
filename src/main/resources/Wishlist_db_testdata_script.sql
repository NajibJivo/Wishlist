-- Indsæt testbrugere
INSERT INTO User(email, name, password) VALUES
('ayan@example.com', 'Ayan', 'hashed_password_1'),
('daniel@example.com', 'Daniel', 'hashed_password_2'),
('lawand@example.com', 'Lawand', 'hashed_password_3'),
('najib@example.com', 'Najib', 'hashed_password_4');

-- Indsæt test ønskesedler
INSERT INTO Wishlist(name, description, image_url, user_id) VALUES
('Ayans Ønskeseddel', 'Ønsker til Eid','https://example.com/img1.jpg' , 1),
('Daniels Ønskeseddel', 'Ønsker til Gudsmoder fødsel','https://example.com/img2.jpg',2),
('Lawand Ønskeseddel', 'Ønsker til Eid','https://example.com/img3.jpg' ,3),
('Najib Ønskeseddel', 'Ønsker til fødselsdag','https://example.com/img4.jpg',4);

-- Indsæt test produkter
INSERT INTO Product (name, description, price, link) VALUES
('iPhone 16', 'Nyeste iPhone model', 9999.99, 'https://apple.com/iphone 16'),
('PS5', 'Sony PlayStation 5', 4999.99, 'https://playstation.com/ps5'),
('Louis Vuitton', 'Stitch Print Embroidered Sweatshirt', 2490.00, 'https://arcecarry.myshopify.com/products/louis-vuitton-stitch-print-embroidered-sweatshirt'),
('Bærbar PC', 'Lenovo™ ThinkPad X9 Aura Edition', 15634.99, 'https://www.proshop.dk/Baerbar/Lenovo-Thinkpad-X9-14-Gen-1-14-Core-Ultra-7-32GB-1TB');


-- Tilknyt produkter til ønskesedler
INSERT INTO Wishlist_Product (wishlist_id, product_id) VALUES
(1,1), -- Ayan ønsker iPhone 16
(1,2); -- Ayan ønsker PS5

-- Daniels ønsker PS5 og bærbar PC
INSERT INTO Wishlist_Product (wishlist_id, product_id) VALUES
(2,2), -- Ayan ønsker iPhone 16
(2,4); -- Ayan ønsker PS5

-- Lawand  en Louis Vuitton sweatshirt og ønsker en bærbar PC 
INSERT INTO Wishlist_Product (wishlist_id, product_id) VALUES
(3,4), -- Lawand ønsker Lenovo ThinkPad X9
(3,3);  -- Lawand ønsker en sweatshirt

-- Najib ønsker en sweatshirt og iPhone 16 
INSERT INTO Wishlist_Product (wishlist_id, product_id) VALUES
(4,3), -- Najib ønsker en sweatshirt
(4,1); -- Najib ønsker en iPhone 16
