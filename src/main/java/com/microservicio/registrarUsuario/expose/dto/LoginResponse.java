package com.microservicio.registrarUsuario.expose.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.List;


/**
 * Los campos nulos no se van a devolver.
 * Cuando generamos token desde refresh token no le pasamos todos los parametros.
 */

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
public class LoginResponse {

    private String username;
    private  List<String> roles;
    private String token;
    private String refreshToken;

}
