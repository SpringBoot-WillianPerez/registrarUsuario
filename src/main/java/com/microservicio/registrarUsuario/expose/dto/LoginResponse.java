package com.microservicio.registrarUsuario.expose.dto;

import java.util.List;

public record LoginResponse(String username, List<String> roles, String token, String refreshToken) {

}
