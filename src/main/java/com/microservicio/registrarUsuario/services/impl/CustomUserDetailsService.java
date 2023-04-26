package com.microservicio.registrarUsuario.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * La clase unicamente nos buscará el usuario en base de datos y si no lo encuentra nos lanza una excepcion.
 * El recorrido desde aqui sería:
 *  1. Entra en el metodo loadUserByUsername.
 *  2. Se encuentra con userService que es el servicio que hemos implementado para la entidad User
 *  3. Se mueve a la clase UserService y entra en el método findByUser(username)
 *  4. El método findByUser(username) de UserService se mueve a UserRepository.
 *  5. UserRepository tiene un método ( Optional<User> findByUsername(String username); ) que buscará en la BBDD
 *  6. Si encuentra el usuario nos lo devuelve. Si no lo encuentra nos da una excepción
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "no encontrado"));
    }
}
