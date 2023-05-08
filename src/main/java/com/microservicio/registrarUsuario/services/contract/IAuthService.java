package com.microservicio.registrarUsuario.services.contract;

import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;

import java.util.Optional;
import java.util.Set;


public interface IAuthService {


    Optional<User> findByUser(String username);

    GetUserDTO save(CreateUserDTO createUserDTO, Set<UserRole> roles);



}
