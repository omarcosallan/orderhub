ALTER TABLE tb_sales DROP CONSTRAINT fk_customer;

ALTER TABLE tb_costumers RENAME TO tb_customers;

ALTER TABLE tb_sales ADD CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES tb_customers(id);