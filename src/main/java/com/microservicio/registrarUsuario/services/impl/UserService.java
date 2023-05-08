package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.exceptions.IdUserNotFoundException;
import com.microservicio.registrarUsuario.exceptions.NombreExistenteException;
import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserPasswDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUsernameDTO;
import com.microservicio.registrarUsuario.mapstruct.IUserMapper;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import com.microservicio.registrarUsuario.persistence.repository.UserRepository;
import com.microservicio.registrarUsuario.services.contract.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final IUserMapper iUserMapper; //mapeador

    @Override
    public Optional<User> findByUser(String username) {

        return userRepository.findByUsername(username);

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
                userRepository.save(user);
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
    public GetUserDTO editEmail(UpdateUsernameDTO updateUsernameDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdUserNotFoundException(id));

        user.setEmail(updateUsernameDTO.getEmail());
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
