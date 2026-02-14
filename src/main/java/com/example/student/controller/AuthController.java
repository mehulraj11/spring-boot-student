package com.example.student.controller;

import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;
import com.example.student.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Users> registerUser(@RequestBody Users user){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
       return ResponseEntity.status(HttpStatus.OK).body(authService.createToken(authRequest));
    }

    @GetMapping("/root")
    public String rootController(){
        return "working fine";
    }
}
