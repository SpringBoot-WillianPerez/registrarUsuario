package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.exceptions.IdUserNotFoundException;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserPasswDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserEmailDTO;
import com.microservicio.registrarUsuario.mapstruct.IUserMapper;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.repository.UserRepository;
import com.microservicio.registrarUsuario.services.contract.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IUserMapper iUserMapper;
    private final PasswordEncoder passwordEncoder;


    /**
     INICIO Buscar usuario
     */
    @Override
    public List<GetUserDTO> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> iUserMapper.mapToDto(user))
                .collect(Collectors.toList());

    }

    @Override
    public GetUserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdUserNotFoundException(id));

        GetUserDTO getUserDTO = iUserMapper.mapToDto(user);
        return getUserDTO;
    }
    /**
     FIN Buscar usuario
     */


    /**
     INICIO Modificar email y/o password
     */
    @Override
    public GetUserDTO editEmail(UpdateUserEmailDTO updateUserEmailDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdUserNotFoundException(id));

        user.setEmail(updateUserEmailDTO.getEmail());
        userRepository.save(user);

        return iUserMapper.mapToDto(user); //Me devuelve un GetUsetDTO
    }

    @Override
    public GetUserDTO editPassw(UpdateUserPasswDTO updateUserPasswDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdUserNotFoundException(id));

        user.setPassword(passwordEncoder.encode(updateUserPasswDTO.getPassword()));
        userRepository.save(user);

        return iUserMapper.mapToDto(user); //Me devuelve un GetUsetDTO
    }

    /**
     FIN Modificar email y/o password
     */


    /**
     INICIO Borrar Usuario
     */
    @Override
    public void deleteById(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }else{
            throw  new IdUserNotFoundException(id);
        }
    }

    @Override
    public void delete(User user) {
        deleteById(user.getId());
    }

    /**
     FIN Borrar Usuario
     */
}
