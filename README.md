```markdown
# E-Commerce REST API

A complete Spring Boot e-commerce REST API with full CRUD operations and layered architecture.

## Features

- **Product Management**: Create, read, update, and delete products
- **User Management**: User registration, profile updates, and password changes
- **Category System**: Organize products by categories
- **RESTful Design**: Proper HTTP methods and status codes
- **Layered Architecture**: Clean separation of concerns

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3** - Application framework
- **Spring Data JPA** - Database operations
- **Hibernate** - ORM implementation
- **MapStruct** - Object mapping
- **Lombok** - Code reduction
- **H2 Database** - In-memory database (development)
- **Maven** - Dependency management

## Project Architecture

The application follows a seven-layer architecture:

1. **Controller Layer** - Handles HTTP requests and responses
2. **Service Layer** - Contains business logic and rules
3. **Repository Layer** - Manages database operations
4. **Entity Layer** - Maps Java objects to database tables
5. **DTO Layer** - Controls data transfer between layers
6. **Mapper Layer** - Converts between entities and DTOs
7. **Exception Layer** - Handles errors consistently

## API Endpoints

### Products
- `GET /products` - Get all products (optional category filter)
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create new product
- `PUT /products/{id}` - Update existing product
- `DELETE /products/{id}` - Delete product

### Users
- `GET /users` - Get all users (with sorting)
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create new user
- `POST /users/bulk` - Create a bacth of users
- `PUT /users/{id}` - Update user profile
- `DELETE /users/{id}` - Delete user
- `POST /users/{id}/change-password` - Change user password

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation
1. Clone the repository
2. Run `mvn clean install`
3. Start the application with `mvn spring-boot:run`
4. Access the API at `http://localhost:8080`

### Testing the API
Use tools like Postman or curl to interact with the endpoints. The H2 console is available at `http://localhost:8080/h2-console` for database inspection.

## Key Features Implemented

- Proper error handling with custom exceptions
- Password encryption with BCrypt
- Input validation and business rule enforcement
- RESTful resource naming and HTTP semantics
- Clean code separation with dependency injection

Inspired by https://github.com/mosh-hamedani/spring-api-starter
```
