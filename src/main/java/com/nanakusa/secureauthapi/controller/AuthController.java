package com.nanakusa.secureauthapi.controller;

import com.nanakusa.secureauthapi.dto.AuthResponse;
import com.nanakusa.secureauthapi.dto.LoginRequest;
import com.nanakusa.secureauthapi.dto.RefreshTokenRequest;
import com.nanakusa.secureauthapi.dto.UserDto;
import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.UserRepository;
import com.nanakusa.secureauthapi.service.AuthService;
import com.nanakusa.secureauthapi.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        return authService.login(request.getEmail(), request.getPassword(), httpRequest);
    }

    // POST - Registrar usuario (solo para produccion) ❗❗ **IMPORTANTE** Eliminar despues
    @PostMapping("/create")
    public User createUser(@RequestBody UserDto userDto) {
        User user = new User();

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword_hash(hashedPassword);
        user.setRole(userDto.getRole());

        return userRepository.save(user);
    }

    // POST - Obtener nuevo token
    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request.getRefreshToken());
    }

    // POST - Revocar token
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("Logout exitoso");
    }
}
