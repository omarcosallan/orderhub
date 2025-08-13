ALTER TABLE tb_customers ADD owner_ID BIGSERIAL;

ALTER TABLE tb_customers ADD CONSTRAINT fk_customers_users_owner_id FOREIGN KEY (owner_id) REFERENCES tb_users(id);

ALTER TABLE tb_customers ADD CONSTRAINT unique_owner_id UNIQUE (owner_id);

ALTER TABLE tb_customers ALTER COLUMN owner_id SET NOT NULL;