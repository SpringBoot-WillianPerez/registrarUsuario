package com.microservicio.registrarUsuario.mapstruct;

import com.microservicio.registrarUsuario.expose.dto.CreateUserDTO;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.entities.UserRole;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    /**
     * Target (objetivo)  hace referencia al atributo de la clase donde se guarda el dato
     * Source (fuente)    hace referencia al atributo de la clase de la que se pilla el dato
     */
     @Mappings({

            @Mapping(target = "username", source = "username"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "roles", source = "roles")
    })
    GetUserDTO mapToDto(User user);


//    @Mappings({
//
//            @Mapping(target = "username", source = "username"),
//            @Mapping(target = "email", source = "email"),
//            @Mapping(target = "password", source = "PasswordEncoder.passwordEncoder.encode(password)"),
//            @Mapping(target = "roles", source = "roles")
//    })
//     User mapToEntity(CreateUserDTO createUserDTO);

}
