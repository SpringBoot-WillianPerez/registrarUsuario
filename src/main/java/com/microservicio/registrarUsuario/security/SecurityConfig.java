package com.microservicio.registrarUsuario.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {


    /**
        Cuando hago un crud LO PRIMERO que hará es venir aquí y pasar por el filtro.
        Después vuelve al controlador y empieza a moverse por los métodos de las
        diferentes clases.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests().anyRequest()
//                .authenticated()
//                .and().formLogin().loginPage("/login").permitAll();

        http.csrf().disable()//Deshabilitar sesion. Aqui estamos usando tokens (sin control de estados)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .mvcMatchers("/auth/**")
                .permitAll() //Habilitamos controladores.//Autorizamos Controladores. En este caso controlador (/auth/**).
                .anyRequest()
                .authenticated();

        return http.build();
    }

}
