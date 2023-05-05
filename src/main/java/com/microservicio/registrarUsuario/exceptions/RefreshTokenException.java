package com.microservicio.registrarUsuario.exceptions;

/**
 * Va a controlar las situaciones de excepciones: SI HA CADUCADO, SI NO LO PODEMOS ENCONTRAR
 */

public class RefreshTokenException extends  JwtTokenException{

    public RefreshTokenException(String message) { //Constructor
        super(message);
    }
}
