# Coupon API

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-4.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Docker](https://img.shields.io/badge/Docker-ready-blue)
![License](https://img.shields.io/badge/license-MIT-green)

API REST para gerenciamento de cupons de desconto, desenvolvida com **Spring Boot**, aplicando boas práticas de arquitetura, validação de regras de negócio, testes automatizados e containerização com Docker.

Este projeto foi desenvolvido como **desafio técnico backend**, demonstrando organização arquitetural, testes de integração, versionamento de banco de dados e documentação da API.

---

# Tecnologias Utilizadas

## Backend

- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Bean Validation

## Banco de Dados

- PostgreSQL
- Flyway (versionamento de schema)

## Mapeamento

- MapStruct

## Redução de Boilerplate

- Lombok

## Documentação da API

- Springdoc OpenAPI
- Swagger UI

## Testes

- JUnit 5
- Spring Boot Test
- MockMvc
- Testcontainers

## Infraestrutura

- Docker
- Docker Compose

---

# Endpoints da API

Base URL http://localhost:8080

---

## Criar Cupom
POST api/v1/coupon

### Request

```json
{
  "code": "ABC123",
  "description": "Cupom de lançamento",
  "discountValue": 20.00,
  "expirationDate": "2026-12-01T12:00:00Z",
  "published": true
}
```
Response
201 Created

```json
{
  "id": "cef9d1e3-aae5-4ab6-a297-358c6032b1e7",
  "code": "ABC123",
  "description": "Cupom de lançamento",
  "discountValue": 20.00,
  "expirationDate": "2026-12-01T12:00:00Z",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}
```
## Buscar Cupom
GET api/v1/coupon/{id}

### Response
200 OK

```json
{
  "id": "cef9d1e3-aae5-4ab6-a297-358c6032b1e7",
  "code": "ABC123",
  "description": "Cupom de lançamento",
  "discountValue": 20.00,
  "expirationDate": "2026-12-01T12:00:00Z",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}
```
## Remover Cupom
DELETE api/v1/coupon/{id}

### Response
204 No Content

## Tratamento de Erros

### A API retorna respostas padronizadas para erros.
####Exemplo

400 Bad Request

```json
{
  "timestamp": "2026-03-08T21:00:00",
  "status": 400,
  "error": "Business rule violation",
  "messages": [
    "Coupon code already exists"
  ]
}
```
## Documentação da API

### Swagger UI disponível em: http://localhost:8080/swagger-ui.html

## Banco de Dados

### O schema é controlado pelo Flyway.

### Migrations localizadas em: src/main/resources/db/migration

## Executando o Projeto
### Requisitos

Java 21+

Maven 3.9+

Docker

## Executar Localmente
```json
mvn clean package
mvn spring-boot:run
```
## Variáveis de Ambiente

### A aplicação suporta configuração por variáveis de ambiente.

Exemplo:
```json
DB_URL=jdbc:postgresql://localhost:5432/coupons_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
```
## Autor

### Vinicius Ribeiro
