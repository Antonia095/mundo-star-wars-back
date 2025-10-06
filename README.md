# 🌟 Mundo Star Wars - API Backend

Uma API REST desenvolvida em Java/Spring Boot para gerenciamento de usuários com tema Star Wars, incluindo sistema de autenticação JWT e diferentes tipos de usuários.

## 🚀 Tecnologias Utilizadas

### Core
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Web** - Para desenvolvimento de APIs REST
- **Spring Security** - Segurança e autenticação
- **Spring Data MongoDB** - Integração com banco de dados MongoDB

### Banco de Dados
- **MongoDB** - Banco de dados NoSQL para persistência de dados

### Documentação
- **Swagger/OpenAPI 3** - Documentação interativa da API
- **SpringDoc OpenAPI** - Integração Swagger com Spring Boot

### Autenticação & Segurança
- **JWT (JSON Web Token)** - Autenticação baseada em tokens
- **Spring Security** - Configuração de segurança

### Utilitários
- **Lombok** - Redução de código boilerplate
- **MapStruct** - Mapeamento entre DTOs e entidades
- **Jakarta Validation** - Validação de dados

### Testes
- **JUnit 5** - Framework de testes
- **Spring Boot Test** - Testes de integração
- **Spring Security Test** - Testes de segurança

### DevOps
- **Docker** - Containerização da aplicação
- **Docker Compose** - Orquestração de containers
- **Gradle** - Gerenciamento de dependências e build

## 📋 Funcionalidades

### 🔐 Autenticação
- **Login de usuários** com email e senha
- **Geração de tokens JWT** para acesso seguro às APIs
- **Validação de credenciais** e retorno de token de acesso

### 👥 Gerenciamento de Usuários
- **Cadastro de usuários** com diferentes tipos (ADMIN, PADRÃO)
- **Validação de dados** de entrada
- **Diferentes níveis de acesso** baseados no tipo de usuário

### 📊 Tipos de Usuário
- **ADMIN** - Usuário administrador com privilégios elevados
- **PADRÃO** - Usuário comum com acesso limitado

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas estruturada em:

```
src/main/java/br/com/projeto/mundo_star_wars/
├── config/          # Configurações (Security, etc.)
├── controller/      # Controllers REST
├── dto/            # Data Transfer Objects
├── enums/          # Enumerações
├── exception/      # Tratamento de exceções
├── mapper/         # Mapeadores MapStruct
├── model/          # Entidades do domínio
├── repository/     # Repositórios de dados
└── service/        # Lógica de negócio
```

### 📡 Endpoints Principais

#### Autenticação
- `POST /api/autenticacao/login` - Realizar login e obter token JWT

#### Usuários
- `POST /api/usuarios/cadastro` - Cadastrar novo usuário

## 🐳 Executando com Docker

### Pré-requisitos
- Docker
- Docker Compose

### Executar a aplicação
```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd mundo-star-wars

# Executar com Docker Compose
docker-compose up -d
```

A aplicação estará disponível em: `http://localhost:8080`

## 🔧 Executando Localmente

### Pré-requisitos
- Java 17
- MongoDB rodando na porta 27017
- Gradle

### Passos para execução
```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd mundo-star-wars

# Executar o MongoDB (se não estiver rodando)
docker run -d -p 27017:27017 --name mongo mongo

# Executar a aplicação
./gradlew bootRun
```

## 📖 Documentação da API

Após executar a aplicação, acesse:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8080/api-docs`

## 🌐 Configurações

### Banco de Dados
O MongoDB está configurado para rodar na porta padrão `27017` com database `starwars`.

### Segurança
A aplicação utiliza JWT para autenticação. Os tokens são gerados no endpoint de login e devem ser incluídos no header `Authorization` das requisições protegidas.

## 📄 Licença

Este projeto é um projeto de estudos desenvolvido para aprendizado de tecnologias Java/Spring Boot.
