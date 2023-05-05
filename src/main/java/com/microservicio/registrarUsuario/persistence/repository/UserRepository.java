package com.microservicio.registrarUsuario.persistence.repository;
import com.microservicio.registrarUsuario.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository de JPA
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
