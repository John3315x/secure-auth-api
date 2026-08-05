# 🔐 Secure Auth API

A production-oriented authentication and authorization service built with **Spring Boot** and **Spring Security**, implementing a **stateless security architecture** based on **JWT**, **Refresh Tokens**, and **Role-Based Access Control (RBAC)**.

The project demonstrates modern backend authentication practices, secure session management, and scalable API design.

---

## 📖 Overview

Secure Auth API provides a complete authentication solution for RESTful applications by combining short-lived **JWT Access Tokens** with persistent **Refresh Tokens** stored in a relational database.

Unlike traditional server-side sessions, this approach enables scalable authentication while maintaining full control over active user sessions.

### Core Capabilities

* JWT-based authentication
* Database-backed Refresh Tokens
* Stateless security architecture
* Role-Based Authorization (RBAC)
* Secure logout with token revocation
* Multi-device session support
* BCrypt password hashing

---

# 🏛️ System Architecture

![System Architecture](resources/5.png)

---

# 🔄 Authentication Flow

The authentication process follows a secure token-based workflow:

1. The user authenticates using their credentials.
2. The server validates the credentials.
3. A short-lived **JWT Access Token** is generated.
4. A long-lived **Refresh Token** is stored in the database.
5. Every protected request is authenticated using the JWT.
6. When the Access Token expires, a new one can be issued through the Refresh Token without requiring the user to log in again.

![Authentication Flow](resources/4.png)

---

# 🗄️ Database Design

The authentication system persists refresh tokens to provide complete session management.

Each active session stores information such as:

* Associated user
* Expiration date
* Revocation status
* Client IP address

This allows administrators to track and invalidate sessions when necessary.

![Database Model](resources/1.png)

---

# ⚙️ Technology Stack

| Technology          | Purpose                        |
| ------------------- | ------------------------------ |
| **Java 17**         | Programming Language           |
| **Spring Boot**     | REST API Framework             |
| **Spring Security** | Authentication & Authorization |
| **Spring Data JPA** | Data Persistence               |
| **Hibernate**       | ORM                            |
| **MySQL**           | Database                       |
| **JWT**             | Stateless Authentication       |
| **BCrypt**          | Password Hashing               |
| **Maven**           | Dependency Management          |

---

# 🔒 Security Features

* JWT authentication for every protected request
* Refresh Token lifecycle management
* Session revocation (logout)
* Role-Based Access Control (RBAC)
* BCrypt password encryption
* IP address registration for active sessions
* Stateless request processing
* Secure authorization filters

---

# 📌 REST API

## Authentication

| Method | Endpoint         | Description                 |
| ------ | ---------------- | --------------------------- |
| POST   | `/auth/register` | Register a new user         |
| POST   | `/auth/login`    | Authenticate user           |
| POST   | `/auth/refresh`  | Generate a new Access Token |
| POST   | `/auth/logout`   | Revoke current session      |

---

## Protected Resources

Authenticated requests must include:

```http
Authorization: Bearer <access_token>
```

---

# 🧪 API Examples

### Login Request

![Login](resources/2.png)

### API Usage

![Examples](resources/3.png)

---

# 🏗️ Design Principles

The project follows several backend development best practices:

* Separation of concerns
* Layered architecture
* Stateless authentication
* DTO-based communication
* Database-driven session management
* Dependency Injection
* Repository Pattern
* RESTful API design

---

# 📈 Scalability Considerations

This authentication service was designed with scalability in mind by:

* Eliminating server-side HTTP sessions
* Supporting multiple simultaneous devices
* Persisting refresh tokens independently
* Using short-lived JWTs
* Allowing token revocation without affecting other sessions

---

# 🚀 Future Enhancements

Potential improvements include:

* OAuth2 / OpenID Connect integration
* Two-Factor Authentication (2FA)
* UserDetailsService customization
* Global exception handling (`@ControllerAdvice`)
* Audit logging
* Device fingerprinting
* Session management dashboard
* Rate limiting
* Email verification
* Password recovery flow

---

# 👨‍💻 Learning Objectives

This project was developed to gain practical experience with:

* Authentication and Authorization
* Spring Security
* REST API Security
* JWT and Refresh Token workflows
* Secure password storage
* Session lifecycle management
* Backend architecture patterns

---

# 📄 License

This project is intended for educational purposes and portfolio demonstration.

---

> **Secure Auth API** demonstrates the implementation of a modern authentication service following industry-standard security practices, making it a solid foundation for enterprise-grade Spring Boot applications.
