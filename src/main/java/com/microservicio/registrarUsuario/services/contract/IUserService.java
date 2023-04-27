package com.microservicio.registrarUsuario.services.contract;

import com.microservicio.registrarUsuario.persistence.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface IUserService {

    Optional<User> findByUser(String username);

    User save(User user);
}
