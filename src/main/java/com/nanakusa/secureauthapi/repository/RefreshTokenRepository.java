package com.nanakusa.secureauthapi.repository;


import com.nanakusa.secureauthapi.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUserEmail(String email);
    List<RefreshToken> findByUserEmailAndRevokedFalse(String email);
    List<RefreshToken> findByUserEmailAndRevokedFalseAndExpiryDateAfter(String email, LocalDateTime now);

    List<RefreshToken> findByRevokedFalse();//Pueden estar expirados
    List<RefreshToken> findByRevokedFalseAndExpiryDateAfter(LocalDateTime now);//Solo activos (no revocados + no expirados)
    List<RefreshToken> findByRevokedTrue();
}
