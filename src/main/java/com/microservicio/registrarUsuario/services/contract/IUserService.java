package com.microservicio.registrarUsuario.services.contract;

import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface IUserService {


    Optional<User> findByUser(String username);

    GetUserDTO save(CreateUserDTO createUserDTO, Set<UserRole> roles);


}
