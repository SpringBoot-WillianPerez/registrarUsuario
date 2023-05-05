package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.exceptions.UserNotFoundException;
import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.expose.dto.LoginRequest;
import com.microservicio.registrarUsuario.expose.dto.LoginResponse;
import com.microservicio.registrarUsuario.persistence.entities.RefreshToken;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.security.access.JwtTokenProvider;
import com.microservicio.registrarUsuario.services.impl.RefreshTokenService;
import com.microservicio.registrarUsuario.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenService refreshTokenService;

    /**
     * REGISTER
     */

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDTO> save(@RequestBody CreateUserDTO newuser){
            return new ResponseEntity<>(userService.save(newuser), HttpStatus.CREATED);
    }


    /**
     LOGIN
     */
    @PostMapping("/auth/login")
    public LoginResponse loginResponse(@RequestBody LoginRequest loginRequest){
    Authentication authenticationDTO = null;
        try {
            //Añadimos el usuario y contraseña del usuario que se va a loguear para, con estos datos, posteriormente Autenticarse
            authenticationDTO = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        }catch (BadCredentialsException ex){

            throw ex;

        }
        //Para hacer la autenticación
        /**
         * Invoca a UserDetailsService para sacar de BBDD
         *  1. Nombre de Usuario
         */
        Authentication authentication = authManager.authenticate(authenticationDTO);
        User user = (User) authentication.getPrincipal();

        //y aquí GENERAMOS el token
        String token = jwtTokenProvider.generateToken(authentication);

        refreshTokenService.delete(user); //Si tiene algun token de refresco lo borramo
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user); //Creamos token de refresco

        //Ese token generado es el que enviamos al Cliente
        return new LoginResponse(
                user.getUsername(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                token, refreshToken.getToken());

    }

}
