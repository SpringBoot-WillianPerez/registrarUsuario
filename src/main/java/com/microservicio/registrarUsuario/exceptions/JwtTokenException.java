package com.microservicio.registrarUsuario.exceptions;

/**
 * Va a controlar TODAS las situaciones de excepciones: no firmado, alterado, etc
 */
public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String message) {
        super(message);
    }
}
