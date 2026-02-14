package com.example.student.service;

import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;

public interface AuthService {
    Users register(Users user);
    String createToken(AuthRequest authRequest) throws Exception;
}
