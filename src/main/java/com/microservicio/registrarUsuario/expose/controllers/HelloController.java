package com.microservicio.registrarUsuario.expose.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hola mundo";
    }

    @GetMapping("/chao")
    public String chao(){
        return "Adios mundo";
    }
}
