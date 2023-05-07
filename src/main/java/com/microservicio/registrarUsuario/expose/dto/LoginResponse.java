package com.microservicio.registrarUsuario.expose.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

import java.util.List;


//@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponse(String username, List<String> roles, String token, String refreshToken) {


}
