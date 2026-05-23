# Challenge Ford — API de Service Share

API REST desenvolvida em Java com Spring Boot para o **Desafio de Dados Ford**, com foco em impulsionar o fluxo de oficina (Service Share) e a retenção de clientes no Brasil através de análise e soluções inteligentes.

---

## Integrantes

- Heloísa Fleury Jardim - RM556378
- Juan Fuentes Rufino - RM557673
- Rickelmyn de Souza Ruescas - RM556055
- Paulo Henrique Monteiro Golovanevsky - RM555300
- Pedro Henrique Silva Batista - RM558137

---

## Sobre o Projeto

A API simula o recebimento de dados de pós-venda automotivo, permitindo o cadastro e gerenciamento de clientes, ordens de serviço e usuários do sistema. Os dados coletados servem como base para análise de padrões de serviço, identificação de falhas recorrentes e geração de insights para concessionárias Ford.

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4.x
- Spring Security + JWT (JJWT 0.11.5)
- Spring Data JPA
- PostgreSQL
- Flyway (migrations)
- Lombok
- Bean Validation

---

## Segurança

> ⚠️ A implementação de segurança está disponível na branch `feature/security`.

A API utiliza autenticação via **JWT (JSON Web Token)**. Para acessar os endpoints protegidos, é necessário primeiro adquirir um token pelo endpoint de login e enviá-lo no header `Authorization` no formato:

```
Authorization: Bearer <token>
```

O token tem validade de **1 hora**.

---

## Como Executar

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL rodando localmente

### Configuração

Configure as variáveis de conexão no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/challenge
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
jwt.secret=sua_chave_secreta
```

### Executando

```bash
./mvnw spring-boot:run
```

As migrations do Flyway serão executadas automaticamente ao subir a aplicação.

---

## Endpoints

### Health Check

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/v1/health` | Verifica se a API está no ar | Não |

---

### Segurança

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/auth/login` | Adquire o token JWT | Não |

**Exemplo de requisição:**
```json
{
  "nomeUsuario": "joao.admin",
  "senha": "Senha@123"
}
```

**Exemplo de resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Clientes

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/clientes` | Criar cliente | Sim |
| GET | `/api/v1/clientes` | Listar clientes ativos | Sim |
| GET | `/api/v1/clientes/inativos` | Listar clientes não ativos | Sim |
| GET | `/api/v1/clientes/{id}` | Exibir detalhes de um cliente | Sim |
| PUT | `/api/v1/clientes/{id}` | Atualizar dados de um cliente | Sim |
| PUT | `/api/v1/clientes/{id}/ativar` | Ativar cliente | Sim |
| DELETE | `/api/v1/clientes/{id}` | Remover cliente | Sim |

**Exemplo de requisição (POST):**
```json
{
  "nome": "João da Silva",
  "cpf": "12345678900",
  "telefone": "11999998888",
  "email": "joao.silva@email.com",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

---

### Ordens de Serviço

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/ordens-servicos` | Adicionar ordem de serviço | Sim |
| GET | `/api/v1/ordens-servicos` | Listar ordens de serviço | Sim |
| GET | `/api/v1/ordens-servicos/{id}` | Exibir ordem de serviço por id | Sim |
| PUT | `/api/v1/ordens-servicos/{id}` | Atualizar ordem de serviço | Sim |
| PUT | `/api/v1/ordens-servicos/{id}/ativar` | Ativar ordem de serviço | Sim |
| DELETE | `/api/v1/ordens-servicos/{id}` | Remover ordem de serviço | Sim |

**Exemplo de requisição (POST):**
```json
{
  "clienteId": 3,
  "concessionariaId": "CONC-SP-044",
  "dataServico": "2025-03-10",
  "tipoServico": "CORRETIVO",
  "veiculo": {
    "veiculoModelo": "Ford Ka",
    "veiculoAno": 2021,
    "veiculoKm": 4000
  },
  "categoriaServico": "FREIOS",
  "tipoFalha": "DESGASTE",
  "descricaoProblema": "Desgaste nas pastilhas de freio dianteiras",
  "valorTotal": 350.00
}
```

**Categorias de serviço disponíveis:** `FREIOS`, `MOTOR`, `SUSPENSAO`, `TRANSMISSAO`, `ELETRICA`, `AR_CONDICIONADO`, `FUNILARIA_PINTURA`, `PNEUS`, `REVISAO_PREVENTIVA`, `OUTROS`

**Tipos de falha disponíveis:** `DESGASTE`, `QUEBRA`, `VAZAMENTO`, `CURTO_CIRCUITO`, `SUPERAQUECIMENTO`, `CORROSAO`, `RECALL`, `REVISAO_PROGRAMADA`, `OUTROS`

---

### Usuários

> Requer role `ADMIN`

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/v1/usuarios` | Cadastrar usuário | Sim |
| GET | `/api/v1/usuarios` | Listar usuários | Sim |
| PUT | `/api/v1/usuarios/{id}` | Atualizar usuário | Sim |
| DELETE | `/api/v1/usuarios/{id}` | Excluir usuário por id | Sim |

**Exemplo de requisição (POST):**
```json
{
  "nomeUsuario": "joao.admin",
  "senha": "Senha@123",
  "role": "FUNCIONARIO"
}
```

---

## Estrutura do Banco de Dados

```sql
-- Clientes
CREATE TABLE clientes (
    id           BIGSERIAL    PRIMARY KEY,
    nome         VARCHAR(200) NOT NULL,
    cpf          VARCHAR(14)  NOT NULL UNIQUE,
    telefone     VARCHAR(20),
    email        VARCHAR(100),
    cidade       VARCHAR(100),
    estado       CHAR(2),
    ativo        BOOLEAN      DEFAULT TRUE
);

-- Ordens de Serviço
CREATE TABLE ordens_de_servico (
    id                 BIGSERIAL      PRIMARY KEY,
    cliente_id         BIGINT         NOT NULL REFERENCES clientes(id),
    concessionaria_id  VARCHAR(20)    NOT NULL,
    data_servico       DATE           NOT NULL,
    tipo_servico       VARCHAR(20)    NOT NULL,
    veiculo_modelo     VARCHAR(100)   NOT NULL,
    veiculo_ano        SMALLINT       NOT NULL,
    veiculo_km         INTEGER,
    categoria_servico  VARCHAR(50),
    tipo_falha         VARCHAR(50),
    descricao_problema TEXT,
    valor_total        DECIMAL(10, 2) NOT NULL,
    ativo              BOOLEAN        DEFAULT TRUE
);

-- Usuários
CREATE TABLE usuarios (
    id             BIGSERIAL    PRIMARY KEY,
    nome_usuario   VARCHAR(100) NOT NULL UNIQUE,
    senha          VARCHAR(255) NOT NULL,
    role           VARCHAR(20)  NOT NULL
);
```

---



## Observações

- Senhas são armazenadas com hash **BCrypt**
- Exclusões de clientes e ordens de serviço são **lógicas** (campo `ativo`), preservando o histórico de dados. A exclusão de usuários é **física** — o registro é removido permanentemente do banco
- As migrations são gerenciadas pelo **Flyway**
- A aplicação utiliza **logs** nas operações principais (cadastro, atualização, exclusão e acesso a listagens) para facilitar o monitoramento e rastreabilidade das ações
