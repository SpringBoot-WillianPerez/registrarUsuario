package com.microservicio.registrarUsuario.expose.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO con la información necesaria para registrar un nuevo usuario en base de datos
 {
 "username": "user1"
 "email": "user1@jwt.io"
 "password": "admin"
 "password2": "admin"
 }
 */

@Getter @Setter
public class CreateUserDTO {

    private String username;

    private String email;

    private String password;

    private String password2;
}
