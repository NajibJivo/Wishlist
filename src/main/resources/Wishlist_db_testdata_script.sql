--  Testdata til Users-tabellen
INSERT INTO Users(email, name, password) VALUES
 ('ayan@example.com', 'Ayan', 'hashed_password_1'),
 ('daniel@example.com', 'Daniel', 'hashed_password_2'),
 ('lawand@example.com', 'Lawand', 'hashed_password_3'),
 ('najib@example.com', 'Najib', 'hashed_password_4');

-- 💾 Testdata til Wishlist-tabellen
INSERT INTO Wishlist(name, description, image_url, user_id) VALUES
('Ayans Ønskeseddel', 'Ønsker til Eid','https://example.com/img1.jpg', 1),
('Daniels Ønskeseddel', 'Ønsker til Gudsmoder fødsel','https://example.com/img2.jpg', 2),
('Lawands Ønskeseddel', 'Ønsker til Eid','https://example.com/img3.jpg', 3),
('Najibs Ønskeseddel', 'Ønsker til fødselsdag','https://example.com/img4.jpg', 4);

-- Testdata til Products-tabellen (med wishlist_id, wish_url og purchase_url)
INSERT INTO products (name, description, price, wishlist_id, wish_url, purchase_url) VALUES

-- Ayan ønsker iPhone og PS5
('iPhone 16', 'Nyeste iPhone model', 9999.99, 1,
 'https://apple.com/iphone-16', 'https://elgiganten.dk/iphone-16'),

('PS5', 'Sony PlayStation 5', 4999.99, 1,
 'https://playstation.com/ps5', 'https://elgiganten.dk/ps5'),

-- Daniel ønsker PS5 og bærbar PC
('PS5', 'Sony PlayStation 5', 4999.99, 2,
 'https://playstation.com/ps5', 'https://elgiganten.dk/ps5'),

('Bærbar PC', 'Lenovo ThinkPad X9 Aura Edition', 15634.99, 2,
 'https://lenovo.com/thinkpad-x9', 'https://proshop.dk/x9'),

-- Lawand ønsker sweatshirt og bærbar PC
('Louis Vuitton', 'Stitch Print Embroidered Sweatshirt', 2490.00, 3,
 'https://arcecarry.myshopify.com/lv', 'https://shopify.com/lv'),

('Bærbar PC', 'Lenovo ThinkPad X9 Aura Edition', 15634.99, 3,
 'https://lenovo.com/thinkpad-x9', 'https://proshop.dk/x9'),

-- Najib ønsker sweatshirt og iPhone
('Louis Vuitton', 'Stitch Print Embroidered Sweatshirt', 2490.00, 4,
 'https://arcecarry.myshopify.com/lv', 'https://shopify.com/lv'),

('iPhone 16', 'Nyeste iPhone model', 9999.99, 4,
 'https://apple.com/iphone-16', 'https://elgiganten.dk/iphone-16');
