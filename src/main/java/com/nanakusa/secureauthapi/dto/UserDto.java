package com.nanakusa.secureauthapi.dto;

import com.nanakusa.secureauthapi.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDto {
    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    public UserDto(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
