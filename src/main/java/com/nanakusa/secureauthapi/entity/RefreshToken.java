package com.nanakusa.secureauthapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔐 Token único (refresh)
    @Column(nullable = false, unique = true)
    private String token;

    // 🔗 Relación con usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ⏳ Expiración
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // IP Address
    @Column(nullable = false)
    private String ip_address;

    // 🚫 Revocado
    @Column(nullable = false)
    private boolean revoked = false;

    // Created At
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
