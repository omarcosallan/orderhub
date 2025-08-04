CREATE TABLE tb_sales (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    seller_id SERIAL NOT NULL,
    customer_id SERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES tb_costumers(id),
    CONSTRAINT fk_seller FOREIGN KEY (seller_id) REFERENCES tb_users(id)
);