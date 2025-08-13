CREATE TABLE tb_sellers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    hiring_date DATE NOT NULL,
    birth_date DATE,
    commission_rate DOUBLE PRECISION,
    phone VARCHAR(20) NOT NULL,
    owner_id BIGINT NOT NULL UNIQUE,

    street VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    complement VARCHAR(255),
    postal_code VARCHAR(20) NOT NULL,

    CONSTRAINT fk_sellers_users_owner_id FOREIGN KEY (owner_id) REFERENCES tb_users (id)
);