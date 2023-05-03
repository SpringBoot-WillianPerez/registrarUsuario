package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.exceptions.NombreExistenteException;
import com.microservicio.registrarUsuario.exceptions.NewUserWithDifferentPasswordsException;
import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.mapstruct.IUserMapper;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import com.microservicio.registrarUsuario.persistence.repository.UserRepository;
import com.microservicio.registrarUsuario.services.contract.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final IUserMapper iUserMapper; //mapeado

    @Override
    public Optional<User> findByUser(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public GetUserDTO save(CreateUserDTO createUserDTO) {
        if(createUserDTO.getPassword().contentEquals(createUserDTO.getPassword2())) {
            User user = new User();
            user.setUsername(createUserDTO.getUsername());
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setRoles(Stream.of(UserRole.USER).collect(Collectors.toSet()));

            try {
                userRepository.save(user);
                GetUserDTO getUserDTO = iUserMapper.mapToDto(user);
                return getUserDTO;

            } catch (DataIntegrityViolationException ex) { //Se usa para el nombre (unique=true)
                throw  new NombreExistenteException();
            }
        }else{
            throw new NewUserWithDifferentPasswordsException(); //Las contraseñas no coinciden
        }
    }
}
