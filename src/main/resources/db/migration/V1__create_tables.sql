CREATE TABLE produtos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    categoria VARCHAR(255) NOT NULL,
    descricao VARCHAR(500),
    estoque INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    preco DOUBLE NOT NULL
);

CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    data_pedido DATETIME(6) NOT NULL,
    status ENUM('CANCELADO','CONCLUIDO','PENDENTE') NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES usuarios(id)
);

CREATE TABLE itens_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    preco_unitario DOUBLE NOT NULL,
    quantidade INT NOT NULL,
    produto_id BIGINT NOT NULL,
    pedido_id BIGINT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produtos(id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

CREATE TABLE usuarios_roles (
    usuario_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (usuario_id, role),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
