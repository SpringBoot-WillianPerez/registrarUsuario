package com.microservicio.registrarUsuario.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {



    /**
     Mensaje de error si el JSON que nos pasan no es correcto
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiError> handleJsonMappingException(JsonMappingException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    @ExceptionHandler(CorreoExistenteException.class)
    public ResponseEntity<ApiError> handleCorreoExistente(CorreoExistenteException ex){

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

    }
}
