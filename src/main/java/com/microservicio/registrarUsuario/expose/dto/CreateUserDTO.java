package com.microservicio.registrarUsuario.expose.dto;

/**
 * DTO con la información necesaria para registrar un nuevo usuario en base de datos
 {
 "username": "user1"
 "email": "user1@jwt.io"
 "password": "admin"
 "password2": "admin"
 }
 */

public record CreateUserDTO(String username, String email, String password, String password2) {
}
