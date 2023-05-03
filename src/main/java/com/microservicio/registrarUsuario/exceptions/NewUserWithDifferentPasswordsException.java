package com.microservicio.registrarUsuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NewUserWithDifferentPasswordsException extends RuntimeException{ //RuntimeException nos evita poner try{}cathc(){}

    public NewUserWithDifferentPasswordsException(){
        super("Las contraseñas no coinciden");
    }
}
