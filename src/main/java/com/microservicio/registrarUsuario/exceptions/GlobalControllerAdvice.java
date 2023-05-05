package com.microservicio.registrarUsuario.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ApiError> handleJwtTokenException(JwtTokenException ex){

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);

    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleRefreshTokenException(RefreshTokenException ex){

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);

    }

//   NO HACE FALTA
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex){
//
//        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
//
//        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
//
//    }
//    NO HACE FALTA
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
//
//        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
//
//        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
//
//    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);

    }

}
