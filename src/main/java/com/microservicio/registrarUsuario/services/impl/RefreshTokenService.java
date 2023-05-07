package com.microservicio.registrarUsuario.services.impl;

import com.microservicio.registrarUsuario.exceptions.RefreshTokenException;
import com.microservicio.registrarUsuario.persistence.entities.RefreshToken;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.repository.RefreshTokenRepository;
import com.microservicio.registrarUsuario.services.contract.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.refresh.expiration}")
    private Long refreshDurationSeconds;


    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        //Fecha de creación se crea sola
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshDurationSeconds));

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    public RefreshToken verify(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Token de refresco Expirado"+ refreshToken.getToken()+ ". Por favor, Login de nuevo ");
        }
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional //para que cuando esté borrando nadie se meta hasta que acabe
    @Override
    public int delete(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }

}
