# Focus

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

## Banco de dados

Banco utilizado:

```text
PostgreSQL
```
