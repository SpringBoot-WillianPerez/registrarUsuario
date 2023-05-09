package com.microservicio.registrarUsuario.expose.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/orders_details")
    public String hello(){
        return "Orders Details";
    }

    @GetMapping("/chao")
    public String chao(){
        return "Adios mundo";
    }
}
