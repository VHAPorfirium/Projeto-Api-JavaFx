# PROJETO-API-JAVAFX

Projeto para a disciplina de Programação Orientada a Objetos com banco de dados. Este projeto integra uma API REST (desenvolvida no projeto integrador do módulo) com uma interface gráfica JavaFX.

## 📋 Descrição

Este projeto consiste em duas partes principais:

1.  Uma **API REST** desenvolvida com Spring Boot, responsável pelo backend e pelas operações CRUD (Create, Read, Update, Delete) em uma base de dados de pacientes.
2.  Uma **Aplicação Desktop JavaFX**, que consome a API REST para fornecer uma interface gráfica para gerenciamento dos dados dos pacientes.

Ambas as partes utilizam PostgreSQL como sistema de gerenciamento de banco de dados.

## 🚀 Tecnologias Utilizadas

**Backend (API REST):**

  * Java 21
  * Spring Boot 3
  * Spring Data JPA
  * Spring Web
  * PostgreSQL (Driver JDBC)
  * Maven (para gerenciamento de dependências e build)
  * SpringDoc OpenAPI (para documentação Swagger)
  
**Frontend (Aplicação Desktop):**

  * Java 21
  * JavaFX

**Banco de Dados:**

  * PostgreSQL

## 🛠️ Requisitos do Sistema

  * JDK 21 ou superior instalado e configurado.
  * Apache Maven 3
  * PostgreSQL instalado e um servidor em execução.

## 📦 Estrutura do Projeto

1.  **API REST (Backend)** - Exemplo de localização: `PROJETO-API-JAVAFX/Projeto-ApiRest-Backend/`
2.  **Aplicação JavaFX (Frontend)** - Exemplo de localização: `PROJETO-API-JAVAFX/Projeto-JavaFx-Cliente/`

### Estrutura da API REST (Backend)

```plaintext
Projeto-ApiRest-Backend/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ApiRestJavaFx/  # Pacote base da sua API
│   │   │       ├── config/
│   │   │       │   ├── SpringDocConfig.java
│   │   │       │   └── WebConfig.java
│   │   │       ├── controller/
│   │   │       │   └── PacienteController.java
│   │   │       ├── dto/
│   │   │       │   └── PacienteDto.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── model/
│   │   │       │   └── Paciente.java         # Entidade JPA
│   │   │       ├── repository/
│   │   │       │   └── PacienteRepository.java
│   │   │       ├── service/
│   │   │       │   └── PacienteService.java  # Corrigido de PacientesService
│   │   │       └── ProjetoApiRestApplication.java # Classe principal da API
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── ... (testes unitários e de integração)
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml       # Arquivo de configuração do Maven para a API
└── README.md     # (Este README ou um específico para a API)
```

### Estrutura da Aplicação JavaFX (Frontend)

```plaintext
Projeto-JavaFx-Cliente/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/vhaporfiro/projetojavafxconsumoapirest/ # Pacote base da app JavaFX
│   │   │       ├── MainApp.java
│   │   │       ├── model/
│   │   │       │   └── Paciente.java       # Modelo/DTO do lado do cliente
│   │   │       ├── controller/
│   │   │       │   ├── PatientListController.java
│   │   │       │   └── PatientFormController.java
│   │   │       └── service/
│   │   │           └── ApiService.java       # Lógica de consumo da API
│   │   └── resources/
│   │       └── br/com/vhaporfiro/projetojavafxconsumoapirest/
│   │           ├── view/
│   │           │   ├── PatientListScreen.fxml
│   │           │   └── PatientFormScreen.fxml
│   │           └── styles/
│   │               └── styles.css
│   └── test/ # (Opcional, para testes da UI)
├── .gitattributes  # (Se for um subprojeto Git separado)
├── .gitignore      # (Se for um subprojeto Git separado)
├── pom.xml         # Arquivo de configuração do Maven para o JavaFX (se usar Maven)
└── README.md       # (README específico para o cliente JavaFX, opcional)
```

## 🔧 Configuração e Execução

### 1\. Backend (API REST)

1.  **Clone o repositório**
2.  **Configure o Banco de Dados PostgreSQL:**
      * Crie um banco de dados
3.  **Script do Banco de Dados:**
      * Execute o script SQL para criar as tabelas necessárias.
4.  **Credenciais do Banco de Dados:**
      * Abra o arquivo `src/main/resources/application.properties` no projeto da API.
5.  **Execute o projeto da API:**
      * Navegue até o diretório do projeto da API (ex: `PROJETO-API-JAVAFX/Projeto-ApiRest-Backend/`).
      * Execute usando Maven:
        ```bash
        mvn spring-boot:run
        ```
      * Ou execute a classe principal `ProjetoApiRestApplication.java` a partir da sua IDE.
      * A API estará normalmente disponível em `http://localhost:8080`.

### 2\. Frontend (Aplicação JavaFX)

1.  **Abra o projeto/módulo JavaFX** na sua IDE.
2.  **Certifique-se de que a API REST (Backend) esteja em execução.**
3.  **Execute a aplicação JavaFX:**
      * Execute a classe `br.com.vhaporfiro.projetojavafxconsumoapirest.MainApp.java` a partir da sua IDE.
        ```bash
        mvn javafx:run
        ```
## 📚 Dependências Principais

### API REST (Backend - `pom.xml`):

  * `spring-boot-starter-data-jpa`: Para persistência de dados com JPA.
  * `spring-boot-starter-validation`: Para validações de dados (ex: anotações em DTOs).
  * `spring-boot-starter-web`: Para desenvolvimento de APIs REST (Spring MVC).
  * `postgresql`: Driver JDBC do PostgreSQL.
  * `springdoc-openapi-starter-webmvc-ui`: Para documentação interativa da API com Swagger.

### Aplicação JavaFX (Frontend - `pom.xml` do cliente, se aplicável):

  * `org.openjfx:javafx-controls`: Componentes de UI do JavaFX.
  * `org.openjfx:javafx-fxml`: Suporte para carregamento de arquivos FXML.
  * `com.fasterxml.jackson.core:jackson-databind`: Para converter objetos Java para JSON e vice-versa.
  * `com.fasterxml.jackson.datatype:jackson-datatype-jsr310`: Suporte para tipos Java Time (LocalDate, Instant) com Jackson.

## 🔍 Documentação da API (Swagger)

Após iniciar a aplicação backend (API REST), a documentação interativa da API estará disponível em:
[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

-----
