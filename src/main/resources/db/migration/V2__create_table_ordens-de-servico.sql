CREATE TABLE ordens_de_servico (
    id                 BIGSERIAL      PRIMARY KEY,
    cliente_id         BIGINT         NOT NULL REFERENCES clientes(id),
    concessionaria_id  VARCHAR(20)    NOT NULL,
    data_servico       DATE           NOT NULL,
    tipo_servico       VARCHAR(20)    NOT NULL,
    veiculo_modelo     VARCHAR(100)   NOT NULL,
    veiculo_ano        INTEGER       NOT NULL,
    veiculo_km         INTEGER,
    categoria_servico  VARCHAR(50),
    tipo_falha         VARCHAR(50),
    descricao_problema TEXT,
    valor_total        DECIMAL(10, 2) NOT NULL,
    ativo              BOOLEAN        DEFAULT TRUE
);