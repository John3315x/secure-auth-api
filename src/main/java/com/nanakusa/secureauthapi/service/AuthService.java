package com.nanakusa.secureauthapi.service;

import com.nanakusa.secureauthapi.dto.AuthResponse;
import com.nanakusa.secureauthapi.entity.RefreshToken;
import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.RefreshTokenRepository;
import com.nanakusa.secureauthapi.repository.UserRepository;
import com.nanakusa.secureauthapi.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IpService ipService;

    public AuthResponse login(String email, String password, HttpServletRequest httpRequest) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword_hash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // SI TODOS VA BIEN SE CREA LA SESION
        // 1.Generar token JWT
        String accessToken = JwtUtil.generateToken(user.getEmail());

        // 2.Se obtiene el IP del cliente
        String ip_address = ipService.getClientIp(httpRequest);

        //3.Se crea el refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user, ip_address);

        return new AuthResponse(accessToken, refreshToken.getToken(), user.getEmail());
    }

    public void logout(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no válido"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}
