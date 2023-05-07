package com.microservicio.registrarUsuario.services.contract;

import com.microservicio.registrarUsuario.persistence.entities.RefreshToken;
import com.microservicio.registrarUsuario.persistence.entities.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface IRefreshTokenService {

    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findByToken(String token);


    int delete(User user);

    RefreshToken verify(RefreshToken refreshToken);
}
