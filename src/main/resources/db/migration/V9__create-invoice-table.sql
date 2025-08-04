CREATE TABLE tb_invoices (
    id SERIAL PRIMARY KEY,
    nfe_ref VARCHAR(255) NOT NULL,
    xml_url VARCHAR(255) NOT NULL,
    danfe_url VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP,
    sale_id BIGINT UNIQUE,

    CONSTRAINT fk_invoice_sale FOREIGN KEY (sale_id) REFERENCES tb_sales(id)
);