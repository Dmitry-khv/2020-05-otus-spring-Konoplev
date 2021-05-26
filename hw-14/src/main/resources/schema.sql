DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;

CREATE TABLE categories
(
    id    IDENTITY PRIMARY KEY,
    title VARCHAR(255)
);

CREATE TABLE products
(
    id            IDENTITY PRIMARY KEY,
    title         VARCHAR(255) UNIQUE,
    description   VARCHAR(255),
    category_id   BIGINT         NOT NULL,
    base_price    DECIMAL(11, 2) NOT NULL,
    current_price DECIMAL(11, 2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);
