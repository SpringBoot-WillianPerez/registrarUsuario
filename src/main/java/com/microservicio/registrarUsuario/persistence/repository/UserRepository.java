package com.microservicio.registrarUsuario.persistence.repository;
import com.microservicio.registrarUsuario.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
