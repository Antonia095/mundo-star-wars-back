# 🌟 Mundo Star Wars - API Backend

Uma API REST desenvolvida em Java/Spring Boot para gerenciamento de usuários e conteúdos com tema Star Wars, incluindo sistema de autenticação JWT, autorização baseada em roles e CRUD completo de conteúdos.

## 🚀 Tecnologias Utilizadas

### Core
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Web** - Para desenvolvimento de APIs REST
- **Spring Security** - Segurança, autenticação e autorização
- **Spring Data MongoDB** - Integração com banco de dados MongoDB
- **Spring Validation** - Validação de dados de entrada

### Banco de Dados
- **MongoDB** - Banco de dados NoSQL para persistência de dados
- **Spring Data MongoDB** - Abstração para operações no MongoDB

### Documentação
- **Swagger/OpenAPI 3** - Documentação interativa da API
- **SpringDoc OpenAPI** - Integração Swagger com Spring Boot
- **Annotations OpenAPI** - Documentação detalhada dos endpoints

### Autenticação & Segurança
- **JWT (JSON Web Token)** - Autenticação baseada em tokens
- **Spring Security** - Configuração de segurança e autorização
- **BCrypt** - Criptografia de senhas
- **Method Security** - Autorização em nível de método com `@PreAuthorize`

### Mapeamento e Utilitários
- **Lombok** - Redução de código boilerplate
- **MapStruct** - Mapeamento automático entre DTOs e entidades
- **Jakarta Validation** - Validação de dados com annotations
- **SLF4J + Logback** - Sistema de logging estruturado

### Testes
- **JUnit 5** - Framework de testes unitários
- **Spring Boot Test** - Testes de integração
- **Spring Security Test** - Testes de segurança com `@WithMockUser`
- **Mockito** - Framework para mocks em testes
- **AssertJ** - Assertions fluentes para testes

### DevOps
- **Docker** - Containerização da aplicação
- **Docker Compose** - Orquestração de containers
- **Gradle** - Gerenciamento de dependências e build

## 📋 Funcionalidades

### 🔐 Autenticação e Autorização
- **Login de usuários** com email e senha
- **Geração de tokens JWT** para acesso seguro às APIs
- **Validação de credenciais** e retorno de token de acesso
- **Autorização baseada em roles** (ADMIN vs PADRÃO)
- **Proteção de endpoints** com diferentes níveis de acesso

### 👥 Gerenciamento de Usuários
- **Cadastro de usuários** com diferentes tipos (ADMIN, PADRÃO)
- **Validação robusta de dados** de entrada
- **Criptografia de senhas** com BCrypt
- **Diferentes níveis de acesso** baseados no tipo de usuário

### 📚 Gerenciamento de Conteúdos
- **CRUD completo de conteúdos** Star Wars
- **Listagem paginada** com ordenação configurável
- **Busca por ID** de conteúdos específicos
- **Validação de unicidade** de títulos
- **Proteção de operações** (apenas ADMINs podem criar/editar/excluir)

### 📊 Tipos de Usuário
- **ADMIN** - Usuário administrador com privilégios completos
  - Pode criar, atualizar e excluir conteúdos
  - Acesso total à API
- **PADRÃO** - Usuário comum com acesso limitado
  - Pode apenas visualizar conteúdos
  - Acesso restrito a operações de leitura

### 🔍 Recursos Avançados
- **Paginação inteligente** com parâmetros configuráveis
- **Ordenação dinâmica** por diferentes campos
- **Tratamento global de exceções** com respostas padronizadas
- **Validação de dados** em múltiplas camadas
- **Logs estruturados** para monitoramento

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java/br/com/projeto/mundo_star_wars/
├── config/          # Configurações (Security, CORS, etc.)
├── controller/      # Controllers REST com documentação OpenAPI
├── dto/            # Data Transfer Objects para requisições/respostas
├── entity/         # Entidades MongoDB (Usuário, Conteúdo)
├── enums/          # Enumerações (TipoUsuario)
├── exception/      # Tratamento global de exceções
├── mapper/         # Mapeadores MapStruct (DTO ↔ Entity)
├── repository/     # Repositórios MongoDB
└── service/        # Lógica de negócio
```

### 📡 Endpoints da API

#### 🔐 Autenticação
- `POST /api/autenticacao/login` - Realizar login e obter token JWT

#### 👥 Usuários
- `POST /api/usuarios/cadastro` - Cadastrar novo usuário

#### 📚 Conteúdos
- `GET /api/conteudos` - Listar conteúdos com paginação (público)
- `GET /api/conteudos/{id}` - Buscar conteúdo por ID (público)
- `POST /api/conteudos` - Criar novo conteúdo (🔒 apenas ADMIN)
- `PUT /api/conteudos/{id}` - Atualizar conteúdo (🔒 apenas ADMIN)
- `DELETE /api/conteudos/{id}` - Excluir conteúdo (🔒 apenas ADMIN)

### 🔒 Sistema de Segurança

#### Autenticação JWT
- Tokens gerados no login com expiração configurável
- Header `Authorization: Bearer <token>` necessário para endpoints protegidos

#### Autorização por Roles
- **Endpoints Públicos**: Listagem e visualização de conteúdos
- **Endpoints Protegidos**: Operações administrativas (CRUD de conteúdos)
- **Validação em tempo real** com `@PreAuthorize`

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

### Executar testes
```bash
# Executar todos os testes
./gradlew test

# Executar apenas testes unitários
./gradlew test --tests "*Unit*"

# Executar apenas testes de integração
./gradlew test --tests "*Integration*"
```

## 📖 Documentação da API

Após executar a aplicação, acesse:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

### 📋 Exemplos de Uso

#### Cadastrar Usuário
```bash
POST /api/usuarios/cadastro
Content-Type: application/json

{
  "nome": "Luke Skywalker",
  "email": "luke@jeditemple.com",
  "senha": "usetheforce123",
  "tipoUsuario": "ADMIN"
}
```

#### Fazer Login
```bash
POST /api/autenticacao/login
Content-Type: application/json

{
  "email": "luke@jeditemple.com",
  "senha": "usetheforce123"
}
```

#### Criar Conteúdo (Admin)
```bash
POST /api/conteudos
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "titulo": "Jedi",
  "descricao": "Guardiões da paz e justiça na galáxia",
  "imagem": "https://starwars.com/jedi.jpg"
}
```

#### Listar Conteúdos
```bash
GET /api/conteudos?page=0&size=10&sortBy=dataCriacao&sortDir=desc
```

## 🌐 Configurações

### Banco de Dados
- **Database**: `starwars`
- **Porta**: `27017`
- **Collections**: `usuarios`, `conteudos`

### Segurança
- **Algoritmo JWT**: HS256
- **Expiração do Token**: Configurável via properties
- **Criptografia de Senha**: BCrypt com força 12

### Logging
- **Nível**: INFO (configurável por ambiente)
- **Formato**: JSON estruturado para produção
- **Saída**: Console + arquivo (configurável)

## 🧪 Testes

O projeto possui cobertura de testes:

### 🔬 Testes Unitários
- **Service Layer**: Testagem da lógica de negócio
- **Mapper Layer**: Validação de mapeamentos
- **Validation**: Testes de validação de dados

### 🔗 Testes de Integração
- **Controller Layer**: Testes end-to-end da API
- **Security**: Validação de autenticação e autorização
- **Database**: Testes com MongoDB integrado

## 🚦 Tratamento de Erros

### Códigos de Status HTTP
- **200** - Sucesso
- **201** - Criado com sucesso
- **204** - Excluído com sucesso
- **400** - Dados inválidos
- **401** - Não autorizado (token inválido/ausente)
- **403** - Acesso negado (role insuficiente)
- **404** - Recurso não encontrado
- **500** - Erro interno do servidor

### Formato de Resposta de Erro
```json
{
  "error": "Descrição do erro",
  "message": "Detalhes adicionais",
  "timestamp": "2024-01-01T12:00:00Z"
}
```

## 📄 Licença

Este projeto é um projeto de estudos desenvolvido para aprendizado de tecnologias Java/Spring Boot, incluindo conceitos avançados de segurança, autorização, testes e boas práticas de desenvolvimento.

---

⭐ *"Que a Força esteja com o código!"* ⭐
