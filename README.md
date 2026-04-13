# 🔐 Secure Auth API

Sistema de autenticación moderno desarrollado con **Spring Boot**, basado en arquitectura **stateless**, utilizando **JWT + Refresh Tokens + Roles** para manejar sesiones seguras y escalables.

---

## 🚀 Descripción

Este proyecto implementa un sistema de autenticación completo similar al usado en aplicaciones reales:

* Autenticación con JWT (access token)
* Manejo de sesiones con Refresh Tokens persistentes en base de datos
* Autorización basada en roles (USER / ADMIN)
* Arquitectura stateless (sin uso de sesiones del servidor)
* Seguridad enfocada en control de sesiones y revocación

---

## 🧠 Problema que resuelve

Los sistemas tradicionales basados en sesiones (`JSESSIONID`) presentan limitaciones:

* No escalan fácilmente en múltiples servidores
* Difícil control de sesiones activas
* Poca visibilidad y control sobre dispositivos

Este proyecto resuelve eso mediante:

✔ Autenticación stateless
✔ Control total de sesiones en base de datos
✔ Revocación de accesos
✔ Soporte para múltiples dispositivos

---

## ⚙️ Tecnologías utilizadas

* Java 17+
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT (Json Web Token)
* BCrypt (hash de contraseñas)

---

## 🔐 Arquitectura de autenticación

### 🧩 Flujo general

1. Login con credenciales
2. Generación de:

   * Access Token (JWT)
   * Refresh Token (guardado en DB)
3. En cada request:

   * Se valida el JWT
   * Se reconstruye el contexto de seguridad
4. Cuando el JWT expira:

   * Se usa el refresh token para obtener uno nuevo

---

## 🔄 Refresh Tokens (sesiones)

Los refresh tokens representan sesiones activas del usuario:

* Se almacenan en base de datos
* Tienen expiración
* Pueden ser revocados (logout)
* Permiten múltiples sesiones por usuario

---

## 🛡️ Seguridad implementada

* Contraseñas hasheadas con BCrypt
* Validación de JWT en cada request
* Control de acceso por roles
* Revocación de sesiones (logout)
* Rotación de refresh tokens
* Registro de IP por sesión

---

## 📌 Endpoints principales

### 🔐 Auth

* `POST /auth/login` → autenticación
* `POST /auth/register` → registro de usuario
* `POST /auth/refresh` → renovar access token
* `POST /auth/logout` → cerrar sesión

---

### 🔒 Protegidos

* Endpoints accesibles según rol (`USER`, `ADMIN`)
* Validación mediante JWT en header:

```
Authorization: Bearer <token>
```

---

## 🧱 Modelo de sesión

Cada sesión se representa en la tabla `refresh_tokens`:

* token (único)
* usuario
* fecha de expiración
* estado (revoked)
* IP de origen

Esto permite:

✔ ver sesiones activas
✔ cerrar sesiones específicas
✔ detectar actividad sospechosa

---

## 🧪 Ejemplo de flujo

1. Usuario inicia sesión
2. Recibe tokens
3. Accede a recursos protegidos
4. Access token expira
5. Usa refresh token
6. Continúa sin volver a loguearse

---

## 📈 Características destacadas

* Arquitectura escalable
* Separación de responsabilidades (Controller / Service / Security)
* Uso de filtros personalizados (JwtFilter)
* Manejo manual de sesiones 
* Preparado para microservicios

---

## ⚠️ Buenas prácticas aplicadas

* No confiar en datos del cliente (email no expuesto en requests)
* Validación contra base de datos
* Uso de DTOs para requests/responses
* Evita uso de sesiones del servidor
* Uso de roles con prefijo `ROLE_`

---

## 🚀 Posibles mejoras

* Implementar UserDetailsService
* Manejo global de errores (`@ControllerAdvice`)
* Auditoría de acciones (logs por usuario)
* Soporte para User-Agent (dispositivo)
* Dashboard de sesiones activas

---

## 👨‍💻 Autor

Desarrollado como proyecto de práctica enfocado en backend moderno, seguridad y arquitectura real de autenticación.

---

## 🧭 Conclusión

Este proyecto demuestra la implementación de un sistema de autenticación completo, seguro y escalable, alineado con prácticas utilizadas en aplicaciones reales.

---
