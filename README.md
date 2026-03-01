# Oficina Fibras Naturais - E-commerce Backend

## Objetivo

Este projeto foi desenvolvido para atender às necessidades do time das Oficinas de Fibras Naturais, que atua na produção de objetos artesanais utilizando o bagaço da cana de açúcar. O objetivo principal é fornecer uma plataforma de e-commerce robusta e eficiente para a venda desses produtos únicos, facilitando o gerenciamento de todo o processo de vendas online, desde o cadastro de produtos até a gestão de pedidos e usuários.

## Descrição do Projeto

A "Oficina Fibras Naturais" é uma iniciativa dedicada à valorização do artesanato sustentável, transformando o bagaço da cana de açúcar em objetos artesanais. Este repositório contém o backend da aplicação de e-commerce, desenvolvido em Java com Spring Boot. A API é responsável por gerenciar:

*   **Autenticação e Autorização**: Sistema de login para usuários e administradores/coordenadores com diferentes níveis de acesso (roles).
*   **Gestão de Usuários**: Cadastro, consulta e atualização de perfis de usuários e endereços.
*   **Gestão de Produtos**: Cadastro, consulta, atualização e remoção de produtos, incluindo upload de imagens.
*   **Gestão de Pedidos**: Criação, acompanhamento e atualização do status de pedidos, com itens e valores associados.
*   **Configurações da Loja**: Gerenciamento de configurações globais da plataforma.
*   **Upload de Imagens**: Integração com Google Cloud Storage para armazenamento de imagens de produtos.

## Tecnologias Utilizadas

*   **Linguagem**: Java 25
*   **Framework**: Spring Boot (Web, Data JPA, Security)
*   **Build Tool**: Maven
*   **Banco de Dados**: PostgreSQL (utilizando Neon DB em produção)
*   **Segurança**: Spring Security (JWT para autenticação)
*   **Armazenamento de Arquivos**: Google Cloud Storage
*   **Containerização**: Docker
*   **Orquestração de Containers (Local)**: Docker Compose
*   **Documentação API**: Swagger/OpenAPI
*   **ID Generator**: NanoId

## Ambiente de Produção

O projeto está implantado em um ambiente de produção escalável e moderno:

*   **Plataforma de Deploy**: Google Cloud Run
*   **Armazenamento de Imagens**: Google Cloud Storage
*   **Banco de Dados**: Neon DB (PostgreSQL serverless)

## Documentação da API (Swagger/OpenAPI)

A documentação interativa da API está disponível via Swagger UI. Uma vez que a aplicação esteja rodando (localmente ou em produção), você pode acessá-la através do seguinte endpoint:

*   `http://localhost:8080/swagger-ui.html`

Acesse este URL no seu navegador para explorar todos os endpoints disponíveis, modelos de dados e testar as requisições diretamente.

## Executando o Projeto Localmente

Siga estas instruções para configurar e executar a aplicação em seu ambiente de desenvolvimento.

### Pré-requisitos

*   Java Development Kit (JDK) 25
*   Apache Maven 3.6 ou superior
*   Docker e Docker Compose (opcional, para rodar o banco de dados localmente)

### 1. Clonar o Repositório

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd Oficina_Fibras_Naturais
```

### 2. Configuração do Banco de Dados

Para rodar a aplicação localmente, você precisará de uma instância do PostgreSQL.

#### Opção A: Usando Docker Compose (Recomendado)

O arquivo `docker-compose.yml` já está configurado para levantar um servidor PostgreSQL.

```bash
docker-compose up -d 
```

Isso criará um banco de dados PostgreSQL acessível em `localhost:5432`.


### 3. Variáveis de Ambiente

A aplicação utiliza perfis de ambiente (`application-dev.properties`, `application-test.properties`). Você precisará configurar as credenciais do banco de dados e outras chaves.

Crie um arquivo `.env` na raiz do projeto ou defina as variáveis de ambiente diretamente no seu sistema. Exemplo de `.env` para o perfil `dev`:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/fibrasnaturais_dev
SPRING_DATASOURCE_USERNAME=your_dev_username
SPRING_DATASOURCE_PASSWORD=your_dev_password
```

### 4. Compilar e Executar a Aplicação

#### Compilar o Projeto

```bash
./mvnw clean install
```

#### Executar a Aplicação (Perfil de Desenvolvimento)

```bash
SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
```

A aplicação estará acessível em `http://localhost:8080`.

### 5. Executar Testes

```bash
./mvnw test
```

## Contribuições

Sinta-se à vontade para abrir issues ou pull requests.

## Licença

Este projeto está licenciado sob a Licença MIT.
