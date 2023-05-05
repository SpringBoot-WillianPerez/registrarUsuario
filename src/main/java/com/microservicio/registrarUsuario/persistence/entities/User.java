package com.microservicio.registrarUsuario.persistence.entities;



import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;



@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    private String username;

    @Column(unique = true)
    private String email;

    private String password;


    @ElementCollection(fetch = FetchType.EAGER) //Relacionamos con UserRole
    @Enumerated(EnumType.STRING) //la enumeracion de roles se almacenará en BBDD como string
    private Set<UserRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //nos devuelve los roles/permisos del usuario
        return roles
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.toString()))//Permisos que tendrá
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
