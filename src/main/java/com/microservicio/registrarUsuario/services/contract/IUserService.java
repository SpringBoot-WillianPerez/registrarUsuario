package com.microservicio.registrarUsuario.services.contract;

import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserPasswDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserEmailDTO;
import com.microservicio.registrarUsuario.persistence.entities.User;

import java.util.List;

public interface IUserService {
    List<GetUserDTO> findAll();

    GetUserDTO findById(Long id);

    GetUserDTO editEmail(UpdateUserEmailDTO updateUserEmailDTO, Long id);

    GetUserDTO editPassw(UpdateUserPasswDTO updateUserPasswDTO, Long id);

    void deleteById(Long id);

    void delete(User user);
}
