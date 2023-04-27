package com.microservicio.registrarUsuario.exceptions;

public class NewUserWithDifferentPasswordsException extends RuntimeException{ //RuntimeException nos evita poner try{}cathc(){}

    public NewUserWithDifferentPasswordsException(){
        super("Las contraseñas no coinciden");
    }
}
