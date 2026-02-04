package com.example.student.controller;

import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;
import com.example.student.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest){return authService.generateToken(authRequest);}
}
