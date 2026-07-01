# Focus

LINK YOUTUBE (https://youtu.be/VUx31noEU-M)

Sistema web desenvolvido como projeto final da disciplina de Programação Orientada a Objetos para Web.

## Sobre o sistema

O Focus é um sistema para gerenciamento de uma academia. Ele permite controlar planos, alunos e treinos, além de apresentar um painel com estatísticas operacionais do sistema.

## Tecnologias utilizadas

- Java
- Servlet/JSP
- Maven
- PostgreSQL
- JDBC
- WildFly
- HTML
- CSS
- JavaScript

## Funcionalidades

- Login e logout
- Controle de sessão
- CRUD de planos
- CRUD de alunos
- CRUD de treinos
- Dashboard com estatísticas
- Relacionamento entre tabelas com chaves estrangeiras
- Validação básica de formulários
- Máscaras para CPF, telefone e valor

## Estrutura MVC

O projeto segue uma organização baseada em MVC:

- `model`: classes que representam as entidades do sistema.
- `dao`: classes responsáveis pelo acesso ao banco de dados.
- `service`: classes responsáveis pelas regras de negócio e validações.
- `controller`: Servlets responsáveis por receber requisições e controlar o fluxo.
- `webapp`: páginas JSP, CSS e JavaScript.

## Requisitos para executar

Antes de executar o projeto, é necessário ter instalado:

- JDK compatível com o projeto, preferencialmente Java 17.
- Apache Maven.
- PostgreSQL.
- WildFly configurado no IntelliJ IDEA.

## Configuração do banco de dados

Crie uma base de dados no PostgreSQL com o nome:

```sql
CREATE DATABASE academia_oliveira;
```

Depois conecte nessa base e execute os scripts abaixo.

### Criação das tabelas

```sql
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE plano (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    descricao TEXT,
    valor NUMERIC(10,2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE,
    endereco VARCHAR(150),
    telefone VARCHAR(20),
    id_plano INTEGER REFERENCES plano(id)
);

CREATE TABLE treino (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER NOT NULL REFERENCES aluno(id),
    nome VARCHAR(80) NOT NULL,
    descricao TEXT NOT NULL,
    dia_semana VARCHAR(20),
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);
```

### Inserts iniciais

```sql
INSERT INTO usuario (nome, email, senha, ativo)
VALUES ('Administrador', 'admin@academia.com', '1234', TRUE);

INSERT INTO plano (nome, descricao, valor, ativo)
VALUES
('Basic', 'Plano inicial com acesso aos recursos essenciais da academia.', 99.00, TRUE),
('Pro', 'Plano intermediário com acompanhamento completo e treinos personalizados.', 149.00, TRUE),
('Elite', 'Plano avançado para alunos de alta performance.', 249.00, TRUE);
```

## Configuração da conexão

Confira os dados de conexão no arquivo:

```text
src/main/java/dao/ConexaoDB.java
```

Por padrão, o projeto usa:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/academia_oliveira";
private static final String USER = "postgres";
private static final String PASSWORD = "1234";
```

Se o seu PostgreSQL utilizar outro usuário ou senha, altere esses valores antes de executar o sistema.

## Como executar no IntelliJ com WildFly

1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o Maven carregar as dependências do `pom.xml`.
3. Configure o WildFly em `Run > Edit Configurations`.
4. Adicione uma configuração de servidor WildFly local.
5. Configure o artefato do projeto como `.war`.
6. Inicie o servidor pelo IntelliJ.
7. Acesse o sistema pelo navegador usando a URL gerada pelo WildFly.

Exemplo comum:

```text
http://localhost:8080/ProjetoFinalPOOW/
```

## Usuário de teste

```text
E-mail: admin@academia.com
Senha: 1234
```

## Observação de segurança

As senhas estão armazenadas em texto puro por simplicidade acadêmica e por seguir o padrão desenvolvido em aula. Em um sistema real, o correto seria armazenar senhas utilizando hash seguro.
