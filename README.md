TestBackend
===========

TestBackend is a Spring Boot application designed to manage customer information efficiently. It features a REST API that performs CRUD operations on customer data, ensuring data integrity through input validation and exception handling. Testcontainers is used for isolated testing environments, and the application integrates seamlessly with a MariaDB database.

Table of Contents
-----------------

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Testing](#testing)
- [Docker Support](#docker-support)
- [API Endpoints](#api-endpoints)

Features
--------

- **Customer Management**: Full CRUD functionality for customer data.
- **Input Validation**: Utilizes Jakarta Bean Validation for request validation.
- **Exception Handling**: Centralized exception handling with meaningful error responses.
- **Testing with Testcontainers**: Provides automated integration tests using isolated database instances.
- **Docker Support**: Easy to deploy with Docker for development and production environments.

Prerequisites
-------------

- **Java**: Version 17 or higher
- **Maven**: Version 3.6 or higher
- **MariaDB**: Version 10.5 or higher (for local development)
- **Docker**: (Optional, for containerized setup)

Installation
------------

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/yourusername/testbackend.git
    cd testbackend
    ```

2. **Build the Project**: Ensure all dependencies are installed and the code compiles without errors.

    ```bash
    mvn clean install
    ```

3. **Set Up the Database**: Create a MariaDB database for the application:

    ```sql
    CREATE DATABASE customerdb;
    ```

4. **Update Configuration**: Edit the **application.properties** file to match your database configuration (username, password, etc.).

Configuration
-------------

The main configuration file, **src/main/resources/application.properties**, contains essential settings:

```properties
        # Server Configuration
        server.port=8099
        
        # Database Configuration
        spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/customerdb
        spring.datasource.username=root
        spring.datasource.password=password
        spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
        
        # JPA & Hibernate Settings
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
        
        # Logging Level
        logging.level.org.springframework=DEBUG
        logging.level.org.hibernate.SQL=DEBUG
```

## Usage
To run the application locally:
```bash
    mvn spring-boot:run
```
## Testing
The project includes unit and integration tests for API endpoints, service classes, and the database layer.
Run Tests

To execute all tests:

```bash
    mvn test
```
#Testcontainers Support
The application uses Testcontainers to create isolated MariaDB instances for testing. Ensure Docker is running to allow Testcontainers to work effectively.
## Docker Support
A **Dockerfile** is provided for containerizing the application.
Build Docker Image
    

```bash
    docker build -t testbackend .
    Run Docker Container
```
```bash
    docker run -p 8099:8099 -d testbackend
```
## api endpoints
The following are the main API endpoints:
- **GET :**  **/customers**: Get all customers.
- **GET:** //**/customers/{id}**: Get a specific customer by ID.
- **POST:** //**/customers**: Create a new customer.
- **PUT:** //**/customers/{id}**: Update a specific customer.
- **DELETE:** //**/customers/{id}**: Delete a specific customer by ID. 
