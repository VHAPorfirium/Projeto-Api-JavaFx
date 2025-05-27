# Projeto-Api-JavaFx
Projeto para faculdade, Poo com banco de dados. Usando parte da APi do projeto integrador do modulo.

## 📋 Descrição
Este é um projeto que combina uma API REST desenvolvida com Spring Boot para ser usada com uma interface gráfica JavaFX. O projeto utiliza PostgreSQL como banco de dados.

## 🚀 Tecnologias Utilizadas
- Java 21
- Spring Boot 3.3.12
- Spring Data JPA
- PostgreSQL
- JavaFX
- Maven
- SpringDoc OpenAPI (Swagger)

## 🛠️ Requisitos do Sistema
- JDK 21 ou superior
- Maven
- PostgreSQL
- IDE compatível com Java (recomendado: IntelliJ IDEA ou Eclipse)

## 📦 Estrutura do Projeto

## Estrutura do Projeto

```plaintext
PROJETO-API-JAVAFX/
├── .vscode/
├── Projeto-ApiRest-com-JavaFx-e-banco-de-dados/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ApiRestJavaFx/
│   │   │           ├── config/
│   │   │           │   ├── SpringDocConfig.java
│   │   │           │   └── WebConfig.java
│   │   │           ├── controller/
│   │   │           │   └── PacienteController.java
│   │   │           ├── dto/
│   │   │           │   └── PacienteDto.java
│   │   │           ├── exception/
│   │   │           │   └── GlobalExceptionHandler.java
│   │   │           ├── model/
│   │   │           │   └── Paciente.java
│   │   │           ├── repository/
│   │   │           │   └── PacienteRepository.java
│   │   │           ├── service/
│   │   │           │   └── PacientesService.java
│   │   │           └── ProjetoApiRestComJavaFxEBancoDeDadosApp.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
├── target/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md


## 🔧 Configuração do Ambiente
1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Execute o script de banco de dados fornecido
4. Configure as credenciais do banco de dados no arquivo `application.properties`
5. Execute o projeto usando Maven:

   mvn spring-boot:run
   

## 📚 Dependências Principais
- `spring-boot-starter-data-jpa`: Para persistência de dados
- `spring-boot-starter-validation`: Para validação de dados
- `spring-boot-starter-web`: Para desenvolvimento da API REST
- `postgresql`: Driver do PostgreSQL
- `springdoc-openapi-starter-webmvc-ui`: Para documentação da API (Swagger)

## 🔍 Documentação da API
A documentação da API está disponível através do Swagger UI. Após iniciar a aplicação, acesse:

http://localhost:8080/swagger-ui.html

## 📝 Notas de Desenvolvimento
- O projeto utiliza Spring Boot 3.x, que requer Java 21
- A interface gráfica vai ser desenvolvida com JavaFX
- O banco de dados PostgreSQL é utilizado para persistência
- A API REST segue os princípios RESTful
- A documentação é gerada automaticamente usando Swagger