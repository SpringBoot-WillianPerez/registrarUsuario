package com.microservicio.registrarUsuario.expose.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String password2;
}
