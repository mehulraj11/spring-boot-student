package com.example.student.service;

import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;

public interface AuthService {
    public Users register(Users user);
    public String generateToken(AuthRequest authRequest);
}
