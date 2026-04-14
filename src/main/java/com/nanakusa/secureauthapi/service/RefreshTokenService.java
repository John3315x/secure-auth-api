package com.nanakusa.secureauthapi.service;

import com.nanakusa.secureauthapi.dto.AuthResponse;
import com.nanakusa.secureauthapi.entity.RefreshToken;
import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.RefreshTokenRepository;
import com.nanakusa.secureauthapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    // Duración: 7 días
    private final long refreshTokenDurationDays = 7;

    public RefreshToken createRefreshToken(User user, String ip_address) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);

        // Token aleatorio
        refreshToken.setToken(UUID.randomUUID().toString());

        // Expiración
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(refreshTokenDurationDays));

        refreshToken.setIp_address(ip_address);

        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    // USADO PARA OBTENER NUEVO TOKEN JWT (refresh token no debe estar vencido ni revocado)
    public AuthResponse refreshToken(String token) {
        // 1. Buscar en DB
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token no válido"));

        // 2. Verificar si está revocado
        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token revocado");
        }

        // 3. Verificar expiración
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expirado");
        }

        // 4. Obtener usuario
        User user = refreshToken.getUser();

        // 5. Generar nuevo access token
        String newAccessToken = JwtUtil.generateToken(user.getEmail());

        // 6. (OPCIONAL PERO PRO) rotar refresh token
        // invalidar el actual
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        // crear uno nuevo
        RefreshToken newRefreshToken = createRefreshToken(user, refreshToken.getIp_address());

        return new AuthResponse(newAccessToken, newRefreshToken.getToken(), user.getEmail());
    }
}
