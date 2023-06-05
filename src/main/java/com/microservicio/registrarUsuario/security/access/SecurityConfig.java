package com.microservicio.registrarUsuario.security.access;


import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.Collections;


@Configuration                     //@PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
@RequiredArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {


    private final JwtFilter jwtFilter;

    /**
        Cuando hago un crud LO PRIMERO que hará es venir aquí y pasar por el filtro.
        Después vuelve al controlador y empieza a moverse por los métodos de las
        diferentes clases.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Aqui se configuran todas las urls de los controladores
        http.authorizeHttpRequests()
                //EndPoints Públicos
                .mvcMatchers("/auth/**")
                .permitAll()

                //EnPoints Privados
                .mvcMatchers("/users/**","/stocks/**", "/products/**",
                                        "/suppliers/**", "/address/**", "/client/**",
                                        "/detail/**", "/order/**", "/status/**").hasRole(UserRole.MASTER.toString())

                .mvcMatchers("/users/**").hasRole(UserRole.ADMIN_USER.toString())

                .mvcMatchers("/products/**").hasRole(UserRole.ADMIN_APP.toString())

                .mvcMatchers("/address/**", "/client/**",
                        "/detail/**", "/order/**", "/status/**").hasRole(UserRole.CLIENT.toString())

                .anyRequest().authenticated()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true).permitAll()
                ;



        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return  http.build();
    }

    /**
        Con este método CREAMOS una petición de autenticación. Este método llamará al SERVICIO
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder passwordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource( new PathPatternParser());
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
