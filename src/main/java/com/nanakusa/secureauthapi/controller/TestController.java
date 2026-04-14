package com.nanakusa.secureauthapi.controller;

import com.nanakusa.secureauthapi.dto.UserDto;
import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // POST - Obtiene el Name() del  contexto de seguridad (subject del token valido), en este caso el email
    @GetMapping("/authenticated")
    public String test() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // POST - Registrar usuario
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
}
