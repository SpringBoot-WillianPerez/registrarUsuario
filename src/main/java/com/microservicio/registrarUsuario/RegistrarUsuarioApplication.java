package com.microservicio.registrarUsuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class RegistrarUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrarUsuarioApplication.class, args);
	}

}
