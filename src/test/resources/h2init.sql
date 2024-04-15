DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS wish_tags;
DROP TABLE IF EXISTS all_tags;

CREATE TABLE users (
     user_id INT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL
);

CREATE TABLE wishes (
    wish_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    link VARCHAR(255),
    shop VARCHAR(255)
);

CREATE TABLE wish_tags (
    tag_number INT AUTO_INCREMENT PRIMARY KEY,
    wish_id INT NOT NULL,
    tag_id INT NOT NULL
);

CREATE TABLE all_tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    wish_id INT NOT NULL,
    tag_value VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password) VALUES
    ('admin', 'admin'),
    ('user1', 'password'),
    ('user2', 'password2'),
    ('user3', 'password3');

INSERT INTO wishes (user_id, name, price, link, shop) VALUES
    (1, 'Macbook Pro', 2000, 'https://www.apple.com/dk/', 'Apple'),
    (1, 'iPhone 12', 800, 'https://www.apple.com/dk/', 'Apple'),
    (2, 'Airpods Pro', 200, 'https://www.apple.com/dk/', 'Apple'),
    (2, 'Apple Watch', 400, 'https://www.apple.com/dk/', 'Apple'),
    (3, 'Macbook Air', 1200, 'https://www.apple.com/dk/', 'Apple'),
    (3, 'iPad Pro', 1000, 'https://www.apple.com/dk/', 'Apple');




