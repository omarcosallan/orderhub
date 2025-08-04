CREATE TABLE tb_items (
    id SERIAL PRIMARY KEY,
    ncm_code VARCHAR NOT NULL UNIQUE,
    name VARCHAR NOT NULL CHECK (char_length(name) >= 5),
    description TEXT,
    slug VARCHAR,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0.01),
    stock_quantity INTEGER NOT NULL,
    type VARCHAR NOT NULL
);
