package com.nanakusa.secureauthapi.controller;

import com.nanakusa.secureauthapi.entity.RefreshToken;
import com.nanakusa.secureauthapi.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/admin/refreshTokens")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    // GET - Obtener todos los refresh tokens
    @GetMapping
    public ResponseEntity<List<RefreshToken>> getRefreshTokens() {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        return ResponseEntity.ok(refreshTokens);
    }

    // GET - Obtener todos los refresh tokens no revocados (puden estar vencidos)
    @GetMapping("/noRevoked")
    public ResponseEntity<List<RefreshToken>> getRefreshTokensNoRevoked() {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByRevokedFalse();//👈 el metodo

        return ResponseEntity.ok(refreshTokens);
    }

    // GET - Obtener todos los refresh tokens revocados
    @GetMapping("/revoked")
    public ResponseEntity<List<RefreshToken>> getRefreshTokensRevoked() {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByRevokedTrue();
        return ResponseEntity.ok(refreshTokens);
    }

    // GET - Obtener todos los refresh tokens por email
    @GetMapping("/byEmail")
    public ResponseEntity<List<RefreshToken>> getRefreshTokensByEmail() {

        //SE OBTIENE EL EMAIL DEL CONTEXTO DE SEGURIDAD (EL CONTEXTO SE CREA SOLO SI EL TOKEN PROPORCIONADO ES VALIDO)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (auth != null) {
            email = auth.getName();
        }

        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserEmail(email);
        return ResponseEntity.ok(refreshTokens);
    }
}
