package com.example.student.service;

import com.example.student.dto.LocalStorageDTO;
import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;

public interface AuthService {
    Users register(Users user);
    LocalStorageDTO createToken(AuthRequest authRequest) throws Exception;
}
