package com.microservicio.registrarUsuario.security.access;

import com.microservicio.registrarUsuario.exceptions.JwtTokenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 Extrae el token jwt de la cabecera de la petición http
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(getClass());

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService; //Es un servicio que proviene de Spring Security core

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     Validar token. Esto es cuando ya quieres ACCEDER a los endpoints (GET, PUT ,ect )
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /**
         * Sacamos nombre de usuario del TOKEN, si ese usuario existe
         * daremos paso a dar el OK en la autenticación
         */

        String token = extractToken(request);

        try {
            if (tokenProvider.isValidToken(token)) {
                /**
                 * 1. Extraemos el usuario del token
                 * 2. Buscamos ese usuario en la BBDD a partir de userDetails
                 */
                String username = tokenProvider.getUsernameFromToken(token); //Campo unico a partir del cual identificamos al usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                /**
                 * Ese usuario (los DATOS del usuario que buscamos por nombre)
                 * lo metemos en la clase Authentication
                 */

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(auth);


            }

            filterChain.doFilter(request, response);
        }catch (JwtTokenException ex){
            log.info("Error de autenticación usando el token jwt"+ ex.getMessage());
            resolver.resolveException(request, response, null, ex);
        }

    }

    private String extractToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");


        if (StringUtils.hasLength(bearer) && bearer.startsWith("Bearer")) {
//            return bearer;
            return bearer.substring("Bearer".length());
        }else {
            return null;
        }

    }
}
