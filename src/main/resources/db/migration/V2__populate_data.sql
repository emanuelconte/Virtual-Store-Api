-- Inserir dados na tabela 'usuarios'
INSERT INTO usuarios (id, email, nome, senha) VALUES
(1, 'admin@lojavirtual.com', 'Admin', '$2a$10$ExampleHash'),
(2, 'cliente@lojavirtual.com', 'Cliente', '$2a$10$ExampleHash');

-- Inserir dados na tabela 'usuarios_roles'
INSERT INTO usuarios_roles (usuario_id, role) VALUES
(1, 'ADMIN'),
(2, 'CLIENTE');

-- Inserir dados na tabela 'produtos'
INSERT INTO produtos (id, nome, descricao, preco, categoria, estoque) VALUES
(1, 'Notebook Dell', 'Notebook Dell Inspiron 15', 3500.00, 'Informática', 10),
(2, 'Smartphone Samsung', 'Smartphone Samsung Galaxy S21', 2500.00, 'Celulares', 20),
(3, 'TV LG 55"', 'Smart TV LG 55 polegadas 4K', 3000.00, 'Eletrônicos', 5),
(4, 'Iphone 15', 'Iphone 15 Pro Max', 6500.00, 'Celulares', 12),
(5, 'Monitor Gamer 27 polegadas', 'MOnitor Samsung', 2500.00, 'Informática', 18);

-- Inserir dados na tabela 'pedidos'
INSERT INTO pedidos (id, cliente_id, data_pedido, status) VALUES
(1, 2, '2025-02-11 10:00:00', 'PENDENTE'),
(2, 2, '2025-02-11 11:00:00', 'CONCLUIDO');

-- Inserir dados na tabela 'itens_pedido'
INSERT INTO itens_pedido (id, preco_unitario, quantidade, produto_id, pedido_id) VALUES
(1, 3500.00, 1, 1, 1),
(2, 2500.00, 2, 2, 1),
(3, 3000.00, 1, 3, 2);