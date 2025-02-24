# API RESTful de Usuarios y Posts

## Descripción 

La API incluye endpoints para:
- Gestión de posts (crear, listar, obtener, actualizar y eliminar)
- Autenticación de usuarios (registro e inicio de sesión)
- Gestión de usuarios (listar, obtener, actualizar y eliminar)

## Funcionalidades Principales

### Autenticación

- `POST /api/auth/register`: Registro de nuevos usuarios
- `POST /api/auth/login`: Inicio de sesión para obtener token JWT

### Usuarios

- `GET /api/users`: Obtener lista de usuarios (requiere autenticación)
- `GET /api/users/{id}`: Obtener detalles de un usuario específico (requiere autenticación)
- `PUT /api/users/{id}`: Actualizar información de usuario (requiere autenticación)
- `DELETE /api/users/{id}`: Eliminar un usuario (requiere autenticación)

### Posts

- `POST /api/posts`: Crear un nuevo post (requiere autenticación)
- `GET /api/posts`: Obtener lista de posts (requiere autenticación)
- `GET /api/posts/{id}`: Obtener detalles de un post específico (requiere autenticación)
- `PUT /api/posts/{id}`: Actualizar un post (requiere autenticación)
- `DELETE /api/posts/{id}`: Eliminar un post (requiere autenticación)


## Documentación de la API

La documentación completa de la API está disponible a través de Swagger UI:

```
http://localhost:8080/api/swagger-ui/index.html#/
```

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.4.3
- Spring Security - Para la autenticación y autorización
- Spring Data JPA - Para el acceso a datos
- MySQL Connector - Para la conexión a la base de datos MySQL
- JWT (JSON Web Token) 0.11.5 - Para la implementación de tokens de autenticación
- SpringDoc OpenAPI 2.8.5 - Para la documentación de la API con Swagger
- Lombok - Para reducir el código boilerplate
- Hibernate Validator 7.0.0.Final - Para la validación de datos
- Spring Boot Validation - Para validación de solicitudes
- Swagger para documentación de API

## Configuración del Entorno

### Base de Datos

1. Crear una base de datos MySQL llamada `demodb`:

```sql
CREATE DATABASE demodb;
```

2. Ejecutar los siguientes scripts para crear las tablas necesarias:

```sql
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

### Clonar el repositorio:

```bash
git clone https://github.com/lucasrodriguez11/TSG-apiRESTful.git
```


## Estructura del Proyecto

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.tsg.application.rest
│   │   │       ├── config/          # Configuraciones (Spring Security, JWT)
│   │   │       ├── controller/      # Controladores REST
│   │   │       ├── model/           # Entidades 
│   │   │       ├── repository/      # Repositorios JPA
│   │   │       ├── service/         # Servicios de negocio
│   │   │       ├── dto/             # Objetos de transferencia de datos
│   │   │       ├── exception/       # Manejo de excepciones
│   │   │       ├── security/        # Implementación de seguridad
│   │   │       └── Application.java
│   │   └── resources/
│   │       └── logback.xml 
│   │       └── application.properties
│   └── 
```

