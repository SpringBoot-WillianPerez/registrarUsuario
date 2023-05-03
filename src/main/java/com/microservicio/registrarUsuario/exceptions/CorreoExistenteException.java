package com.microservicio.registrarUsuario.exceptions;

public class CorreoExistenteException extends RuntimeException{

    public CorreoExistenteException(){
        super("El correo introducido ya existe");
    }
}
