# Omaapinions

Omaapinions is a survey management system designed to enable users to create, share, and analyze surveys easily.

## Features

- User authentication system (Login/Registration)
- Create and manage surveys
- Participate in surveys
- Analyze survey submissions

## Tech Stack

- **Backend:** Java Spring Boot
- **Frontend:** Thymeleaf, HTML, CSS, Bootstrap
- **Database:** MySQL
- **Build Tool:** Maven
- **Server:** XAMPP (for local development)

## Prerequisites

- Java 23 or higher
- Maven
- XAMPP (or any MySQL-compatible database server)
- A modern web browser (e.g. Google Chrome, Mozilla Firefox, Microsoft Edge)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/omaapinions.git
cd omaapinions
```

### 2. Configure Database

1. Install and start XAMPP.
2. Open phpMyAdmin and create a new database named `omaapinions`.
3. Update the `application.properties` file in the `src/main/resources` directory with the following:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/omaapinions
spring.datasource.username=root
spring.datasource.password=
```

   - **Note:** The default username and password (`root` with no password) are used by XAMPP. Adjust if you've configured a different username or password.

### 3. Build and Run the Application

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

## Usage

1. Open the application in your browser at `http://localhost:8080`.
2. Register a new account or log in with an existing one.
3. Create surveys, participate, and analyze submissions.

## Folder Structure

```plaintext
src/main
├── java/com/example/omaapinions
│   ├── controllers
│   ├── models
│   ├── repository
│   ├── service
│   └── OmaapinionsApplication.java
├── resources
│   ├── static
│   ├── templates
│   └── application.properties
└── test/java/com/example/omaapinions
```

- `src/main/java/com/example/omaapinions` - Contains all backend code.
- `src/main/resources/templates` - Thymeleaf templates for frontend pages.

Enjoy using Omaapinions!
