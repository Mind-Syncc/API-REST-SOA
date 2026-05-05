CREATE TABLE clientes (
    id          BIGSERIAL PRIMARY KEY,
    nome        VARCHAR(200) NOT NULL,
    cpf         VARCHAR(14)  NOT NULL UNIQUE,
    telefone    VARCHAR(20),
    email       VARCHAR(100),
    cidade      VARCHAR(100),
    estado      CHAR(2),
    ativo       BOOLEAN      DEFAULT TRUE
);