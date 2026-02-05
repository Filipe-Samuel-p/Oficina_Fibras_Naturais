INSERT INTO tb_role(role_id, authority) VALUES
                                            (1, 'ROLE_ADMIN'),
                                            (2, 'ROLE_CLIENT');


INSERT INTO tb_user(id,name,email,password,phone) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc','Filipe Samuel',
                                                          'felipepires.p125@gmail.com',
                                                        '$2a$10$YjNEj18ApFMUmdjAIzw0KuxRiSxpVKxvhTrY8P7vTC8pd63P8qiGS',
                                                        '22998581484');

INSERT INTO tb_user_role(user_id,role_id) VALUES ('9f4216bb-ccf8-4fad-a30a-dc100855bbcc', '1');