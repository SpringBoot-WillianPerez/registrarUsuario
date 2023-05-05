package com.microservicio.registrarUsuario.security.access;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;

    /**
        Cuando hago un crud LO PRIMERO que hará es venir aquí y pasar por el filtro.
        Después vuelve al controlador y empieza a moverse por los métodos de las
        diferentes clases.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()//Deshabilitar sesion. Aqui estamos usando tokens (sin control de estados)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//deshabilitamos estados. Esta APi es sin estado y sin sesión

        //Aqui se configuran todas las urls de los controladores
        http.authorizeHttpRequests()
                .mvcMatchers("/auth/**")
                .permitAll() //Habilitamos controladores.//Autorizamos Controladores. En este caso controlador (/auth/**).  Esto sirve para que se pueda Autenticar cualquiera (EVIDENTE).
                .anyRequest()
                .authenticated()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true).permitAll()
//                .antMatchers(HttpMethod.GET, "/hello/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/chao/**").hasRole("ADMIN")s
                ;



        //Filtros: intercepta peticiones y las analiza para ver si cumplen las normas/requisitos.
        //Si cumple las normas pasan, si no cumple se bloquea el paso.
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //agrega un filtro de  especificación antes de la posición de la clase .
        //jwtFilter (nuestro filtro): va a evaluar la cabecera Authoritation, sacar el token jwt y validarlo

        return  http.build(); //una vez tratado el objeto lo devolvemos
    }

    /**
        Con este método CREAMOS una petición de autenticación. Este método llamará al SERVICIO
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder passwordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)//AuthenticationManagerBuilder.class Para poder crear objetos de tipo AuthenticationManager
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder) //Para cifrar la contraseña. No se debe almacenar en texto plano
                .and().build();
    }


}
