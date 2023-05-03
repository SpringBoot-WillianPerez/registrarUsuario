package com.microservicio.registrarUsuario.exceptions;

public class NombreExistenteException extends RuntimeException{

    public NombreExistenteException(){
        super("El nombre introducido ya existe");
    }
}
