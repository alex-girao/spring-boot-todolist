# To-Do list

### 🚀 Começando
Aplicação Web para gerenciamento de tarefas(To-Do list), onde é possível adicionar, atualizar, visualizar e deletar tarefas.

## Tecnologias
- [Spring Boot]
- [Spring Web]
- [Spring Data]
- [Spring Security]
- [Hibernate]
- [Swagger]


# Setup da aplicação (local)
### 📋 Pré-requisitos
Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:
- Java 11
- PostgreSQL 16.1
- Maven 3.1.0 

### 🔧 Preparando ambiente
É necessário a criação da base de dados relacional no Postgres para o sistema
```
CREATE DATABASE "db_tarefa";
```
Para os testes de integração também é necessario criar uma base de dados para os testes não interferirem na base de desenvolvimento.
```
CREATE DATABASE "db_tarefa_test";
```

## ⚙️ Instalação da aplicação
Primeiramente, faça o clone do repositório:
```
https://github.com/alex-girao/spring-boot-todolist.git
```
Feito isso, acesse o projeto:
```
cd spring-boot-todolist
```
É preciso compilar o código e baixar as dependências do projeto:
```
mvn clean package
```
Finalizado esse passo, vamos iniciar a aplicação:
```
mvn spring-boot:run
```
Pronto. A aplicação está disponível em http://localhost:8080
```
Tomcat started on port(s): 8080 (http)
Started AppConfig in xxxx seconds (JVM running for xxxx)
```

# Endpoints

O projeto disponibiliza endpoints para 2 contextos diferentes: Usuários e Tarefas, onde utilizam o padrão Rest de comunicação, produzindo e consumindo dados no formato JSON.

A documentação Swagger está disponível em http://localhost:8080/swagger-ui.html

## Tarefa
#### Listar Todos
GET /tarefa
```bash
http://localhost:8080/tarefa/
```
#### Listar por Id
GET /tarefa/:id
```bash
http://localhost:8080/tarefa/1
```
#### Deletar
DELETE /tarefa/:id
```bash
http://localhost:8080/tarefa/1
```
#### Criar
POST /tarefa
```bash
http://localhost:8080/tarefa/
```
Request Body
```bash
{
	"titulo": "Carga de clientes",
	"descricao": "Executar Carga de clientes",
	"status": "PENDENTE"
}
```
#### Atualizar
PUT /tarefa/:id
```bash
http://localhost:8080/tarefa/2
```
Request Body
```bash
{
  "titulo": "Atualizar cadastro de clientes",
  "descricao": "Atualizar todos os cadastros dos clientes",
  "status": "CONCLUIDO"
}
```

## Usuário
#### Criar
POST /usuario
```bash
http://localhost:8080/usuario/
```
Request Body
```bash
{
	"nome": "Alex Girao",
	"email": "alex.girao@email.com",
	"senha": "123"
}
```
#### Listar por Id
GET /usuario/:id
```bash
http://localhost:8080/usuario/1
```

## Usuário
#### Criar
POST /usuario
```bash
http://localhost:8080/usuario/
```
Request Body
```bash
{
	"nome": "Alex Girao",
	"email": "alex.girao@email.com",
	"senha": "123"
}
```

