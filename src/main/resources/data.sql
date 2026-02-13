INSERT INTO tb_role(role_id, authority) VALUES
                                            (1, 'ROLE_ADMIN'),
                                            (2, 'ROLE_CLIENT');


INSERT INTO tb_user(id,name,email,password,phone) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc','Filipe Samuel',
                                                          'felipepires.p125@gmail.com',
                                                        '$2a$10$YjNEj18ApFMUmdjAIzw0KuxRiSxpVKxvhTrY8P7vTC8pd63P8qiGS',
                                                        '22998581484');

INSERT INTO tb_user_role(user_id,role_id) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc', '1');
INSERT INTO tb_user_role(user_id,role_id) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc', '2');


-- Produto 1: Vestuário Básico
INSERT INTO tb_product (name, description, price, stock_quantity, image_url, active)
VALUES ('Camiseta Algodão Premium', 'Camiseta 100% algodão, fio 30.1 penteado. Conforto e durabilidade.', 89.90, 50, 'https://images.exemplo.com/camiseta-preta.jpg', true);

-- Produto 2: Vestuário Casual
INSERT INTO tb_product (name, description, price, stock_quantity, image_url, active)
VALUES ('Calça Jeans Slim Fit', 'Calça jeans com elastano, corte slim, lavagem escura.', 159.00, 30, 'https://images.exemplo.com/calca-jeans.jpg', true);

-- Produto 3: Calçado
INSERT INTO tb_product (name, description, price, stock_quantity, image_url, active)
VALUES ('Tênis Esportivo Runner', 'Ideal para caminhadas e corridas leves. Amortecimento em gel.', 299.99, 15, 'https://images.exemplo.com/tenis-runner.jpg', true);

-- Produto 4: Inverno
INSERT INTO tb_product (name, description, price, stock_quantity, image_url, active)
VALUES ('Moletom Canguru Classic', 'Moletom flanelado com capuz e bolso frontal. Ideal para o inverno.', 189.50, 20, 'https://images.exemplo.com/moletom-cinza.jpg', true);

-- Produto 5: Acessório
INSERT INTO tb_product (name, description, price, stock_quantity, image_url, active)
VALUES ('Boné Aba Curva Minimalist', 'Boné com ajuste traseiro e logo bordado em alta definição.', 59.90, 100, 'https://images.exemplo.com/bone-minimalist.jpg', true);