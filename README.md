TestBackend Application

TestBackend is a Spring Boot application designed for efficient customer management. This application includes a REST API for handling CRUD operations on customer data, with features like input validation, exception handling, and comprehensive unit and integration tests. It integrates with a MariaDB database and uses Testcontainers for isolated database testing environments.
Table of Contents

    Features
    Prerequisites
    Installation
    Configuration
    Usage
    Docker Support
    Endpoints


Features

    Customer Management: Create, read, update, and delete (CRUD) operations for customer data.
    Input Validation: Uses jakarta.validation for request validation.
    Exception Handling: Centralized error handling with meaningful HTTP status codes.
    Testcontainers Integration: Automated database tests with isolated MySQL/MariaDB instances.
    Detailed Logging: Configurable logging levels for enhanced debugging.
    Dockerized Setup: Build and run the application easily with Docker.

Prerequisites

    Java 17 or higher
    Maven 3.6+
    MariaDB 10.5 or higher (if running locally)
    Docker (optional, for containerized setup)

Installation

    Clone the Repository:

    bash

git clone https://github.com/yourusername/testbackend.git
cd testbackend

Build the Project: Ensure all dependencies are installed and the code compiles without errors.

bash

mvn clean install

Set Up the Database: Create a MariaDB database for the application:

sql

    CREATE DATABASE customerdb;

    Update Configuration: Edit the application.properties file to match your database configuration (username, password, etc.).

Configuration

The main configuration file, src/main/resources/application.properties, contains important settings, including:

properties

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

Usage

To run the application locally:

bash

mvn spring-boot:run

The application should now be available at http://localhost:8099.
Testing

The project includes unit and integration tests for API endpoints, service classes, and the database layer.
Run Tests

To execute all tests:

bash

mvn test

Testcontainers Support

The application uses Testcontainers to create isolated MariaDB instances for testing. Ensure Docker is running to allow Testcontainers to work effectively.
Docker Support

A Dockerfile is provided for containerizing the application.
Build Docker Image

bash

docker build -t testbackend .

Run Docker Container

bash

docker run -p 8099:8099 -d testbackend

The application should now be accessible on http://localhost:8099.
Endpoints

The following are the main API endpoints:
Method	Endpoint	Description
GET	/customers	Get all customers
GET	/customers/{id}	Get a specific customer by ID
POST	/customers	Create a new customer
PUT	/customers/{id}	Update a specific customer
DELETE	/customers/{id}	Delete a specific customer by ID
