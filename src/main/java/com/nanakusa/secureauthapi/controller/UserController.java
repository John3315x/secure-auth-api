package com.nanakusa.secureauthapi.controller;

import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> usuarios = userRepository.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

}
