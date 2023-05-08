package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.exceptions.RefreshTokenException;
import com.microservicio.registrarUsuario.expose.dto.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController {




}
