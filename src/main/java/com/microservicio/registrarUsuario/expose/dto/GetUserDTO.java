package com.microservicio.registrarUsuario.expose.dto;

import java.util.Set;

public record GetUserDTO(String username, String password, Set<String> roles) {


}
