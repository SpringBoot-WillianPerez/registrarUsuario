package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDTO> save(@RequestBody CreateUserDTO newuser){

            return new ResponseEntity<>(userService.save(newuser), HttpStatus.CREATED);

    }
}
