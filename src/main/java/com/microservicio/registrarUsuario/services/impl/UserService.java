package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.repository.UserRepository;
import com.microservicio.registrarUsuario.services.contract.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;


    @Override
    public Optional<User> findByUser(String username) {
        return userRepository.findByUsername(username);
    }
}
