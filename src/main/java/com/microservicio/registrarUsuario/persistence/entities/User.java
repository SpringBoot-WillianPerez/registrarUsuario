package com.microservicio.registrarUsuario.persistence.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(unique = true)
    private String username;


    private String email;

    private String password;


    @ElementCollection(fetch = FetchType.EAGER) //Relacionamos con UserRole
    @Enumerated(EnumType.STRING) //la enumeracion de roles se almacenará en BBDD como string
    private Set<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.WRITE.toString()))//Permisos que tendrá
                .toList();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
