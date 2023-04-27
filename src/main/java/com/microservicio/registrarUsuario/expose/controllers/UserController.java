package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity save(@RequestBody User newuser){

            return new ResponseEntity<>(userService.save(newuser), HttpStatus.CREATED);

    }
}
