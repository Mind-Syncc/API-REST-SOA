CREATE TABLE usuarios (
    id             BIGSERIAL    PRIMARY KEY,
    nome_usuario   VARCHAR(100) NOT NULL UNIQUE,
    senha          VARCHAR(255) NOT NULL,
    role           VARCHAR(20)  NOT NULL
);