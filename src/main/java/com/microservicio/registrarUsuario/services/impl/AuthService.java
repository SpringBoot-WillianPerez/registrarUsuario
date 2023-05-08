package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.exceptions.NombreExistenteException;
import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.mapstruct.IUserMapper;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import com.microservicio.registrarUsuario.persistence.repository.AuthRepository;
import com.microservicio.registrarUsuario.services.contract.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    private final IUserMapper iUserMapper; //mapeador

    @Override
    public Optional<User> findByUser(String username) {

        return authRepository.findByUsername(username);

    }



    @Override
    public GetUserDTO save(CreateUserDTO createUserDTO, Set<UserRole> roles) {
            User user = new User();
            user.setUsername(createUserDTO.getUsername());
            user.setEmail(createUserDTO.getEmail());
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setRoles(roles);
//
//            User user1 = new User(createUserDTO.getUsername(),
//                    createUserDTO.getEmail(),
//                    passwordEncoder.encode(createUserDTO.getPassword()),
//                    List.of(UserRole.ADMIN));

            try {
                authRepository.save(user);
                GetUserDTO getUserDTO = iUserMapper.mapToDto(user);
                return getUserDTO;

            } catch (DataIntegrityViolationException ex) { //Se usa para el Nombre de usuario (unique=true)
                throw  new NombreExistenteException();
            }
    }

    public GetUserDTO saveClient(CreateUserDTO createUserDTO){
        return save(createUserDTO, Set.of(UserRole.CLIENT)) ;
    }

    public GetUserDTO saveMaster(CreateUserDTO createUserDTO){
        return save(createUserDTO, Set.of(UserRole.MASTER));
    }

    public GetUserDTO saveAdminApp(CreateUserDTO createUserDTO){
        return save(createUserDTO, Set.of(UserRole.ADMIN_APP)) ;
    }

    public GetUserDTO saveAdminUser(CreateUserDTO createUserDTO){
        return save(createUserDTO, Set.of(UserRole.ADMIN_USER));
    }



}
