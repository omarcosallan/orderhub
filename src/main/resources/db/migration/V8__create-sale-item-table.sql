CREATE TABLE tb_sale_item (
    sale_id SERIAL NOT NULL,
    item_id SERIAL NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    sub_price DECIMAL(10,2) NOT NULL,
    discount DOUBLE PRECISION,

    PRIMARY KEY (sale_id, item_id),
    CONSTRAINT fk_sale FOREIGN KEY (sale_id) REFERENCES tb_sales(id) ON DELETE CASCADE,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES tb_items(id)
);