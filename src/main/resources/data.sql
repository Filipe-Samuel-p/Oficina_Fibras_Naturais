INSERT INTO tb_shop_config(whatsapp_number) VALUES ('5522998581484');

INSERT INTO tb_role(role_id, authority) VALUES
                                            (1, 'ROLE_ADMIN'),
                                            (2, 'ROLE_CLIENT'),
                                            (3,'ROLE_COORDINATOR');


INSERT INTO tb_user(id,name,email,password,phone) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc','Filipe Samuel',
                                                          'felipepires.p125@gmail.com',
                                                        '$2a$10$YjNEj18ApFMUmdjAIzw0KuxRiSxpVKxvhTrY8P7vTC8pd63P8qiGS',
                                                        '22998581484');

INSERT INTO tb_user(id, name, email, password, phone)
VALUES ('e584ce82-6a72-4257-80a1-dbebdd94706f', 'Chefa Coordenadora', 'coord@fibras.com', '$2a$10$YjNEj18ApFMUmdjAIzw0KuxRiSxpVKxvhTrY8P7vTC8pd63P8qiGS', '22999998888');

INSERT INTO tb_user_role(user_id,role_id) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc', '1');
INSERT INTO tb_user_role(user_id,role_id) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc', '2');
INSERT INTO tb_user_role(user_id, role_id) VALUES ('e584ce82-6a72-4257-80a1-dbebdd94706f', '3');


-- Produto 1: Vestuário Básico
INSERT INTO tb_product (name, description, price_per_unit, stock_quantity, image_url, active)
VALUES ('Camiseta Algodão Premium', 'Camiseta 100% algodão, fio 30.1 penteado. Conforto e durabilidade.', 89.90, 50, 'https://images.exemplo.com/camiseta-preta.jpg', true);

-- Produto 2: Vestuário Casual
INSERT INTO tb_product (name, description, price_per_unit, stock_quantity, image_url, active)
VALUES ('Calça Jeans Slim Fit', 'Calça jeans com elastano, corte slim, lavagem escura.', 159.00, 30, 'https://images.exemplo.com/calca-jeans.jpg', true);

-- Produto 3: Calçado
INSERT INTO tb_product (name, description, price_per_unit, stock_quantity, image_url, active)
VALUES ('Tênis Esportivo Runner', 'Ideal para caminhadas e corridas leves. Amortecimento em gel.', 299.99, 15, 'https://images.exemplo.com/tenis-runner.jpg', true);

-- Produto 4: Inverno
INSERT INTO tb_product (name, description, price_per_unit, stock_quantity, image_url, active)
VALUES ('Moletom Canguru Classic', 'Moletom flanelado com capuz e bolso frontal. Ideal para o inverno.', 189.50, 20, 'https://images.exemplo.com/moletom-cinza.jpg', true);

-- Produto 5: Acessório
INSERT INTO tb_product (name, description, price_per_unit, stock_quantity, image_url, active)
VALUES ('Boné Aba Curva Minimalist', 'Boné com ajuste traseiro e logo bordado em alta definição.', 59.90, 100, 'https://images.exemplo.com/bone-minimalist.jpg', true);







-- =================================================================
-- 1. ADICIONAR ENDEREÇO AO USUÁRIO (Filipe Samuel)
-- =================================================================
-- Note que o ID do usuário deve ser o mesmo UUID do seu insert anterior
INSERT INTO tb_address (user_id, street, number, neighborhood, city, zip_code)
VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc',
        'Av. Alberto Lamego',
        '2000',
        'Parque Califórnia',
        'Campos dos Goytacazes',
        '28013-602');

-- =================================================================
-- 2. CRIAR PEDIDOS (Tabela tb_orders)
-- =================================================================
-- Lembre-se: O ID agora é uma STRING (NanoID). Vamos simular alguns.

-- Pedido 1: Compra antiga (Status COMPLETED) - Comprou 2 itens
INSERT INTO tb_order (id, creation_Date, status, total_value, user_id, address_snapshot)
VALUES ('K9X2-M4B9',
        '2023-10-10 14:30:00',
        'COMPLETED',
        149.80,
        '9f4216bb-ccf8-4fad-a30a-dc100855bbcc',
        'Av. Alberto Lamego, 2000 - Parque Califórnia, Campos dos Goytacazes - CEP: 28013-602');

-- Pedido 2: Compra recente (Status PENDING) - Comprou 1 item caro
INSERT INTO tb_order (id, creation_Date, status, total_value, user_id, address_snapshot)
VALUES ('X7Y1-L2P0',
        NOW(),
        'PENDING',
        299.99,
        '9f4216bb-ccf8-4fad-a30a-dc100855bbcc',
        'Av. Alberto Lamego, 2000 - Parque Califórnia, Campos dos Goytacazes - CEP: 28013-602');

-- =================================================================
-- 3. CRIAR ITENS DO PEDIDO (Tabela tb_order_items)
-- =================================================================
-- Os produtos referenciados (product_id) devem existir no seu seed original (1 a 5)

-- Itens do Pedido 1 (K9X2-M4B9):
-- Item A: 1x Camiseta (Prod ID 1) - R$ 89.90
INSERT INTO tb_order_item (quantity, total_price_item, order_id, product_id)
VALUES (1, 89.90, 'K9X2-M4B9', 1);

-- Item B: 1x Boné (Prod ID 5) - R$ 59.90
INSERT INTO tb_order_item (quantity, total_price_item, order_id, product_id)
VALUES (1, 59.90, 'K9X2-M4B9', 5);

-- (Total calculado: 89.90 + 59.90 = 149.80 -> Bate com o total do pedido)


-- Itens do Pedido 2 (X7Y1-L2P0):
-- Item C: 1x Tênis (Prod ID 3) - R$ 299.99
INSERT INTO tb_order_item (quantity, total_price_item, order_id, product_id)
VALUES (1, 299.99, 'X7Y1-L2P0', 3);