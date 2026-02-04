package com.example.student.service.implementation;

import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;
import com.example.student.exception.UserAlreadyExists;
import com.example.student.exception.UserRegistrationException;
import com.example.student.repository.UserRepository;
import com.example.student.service.AuthService;
import com.example.student.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder encoder,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Users register(Users user) {
        log.info("Starting user registration for username: {}", user.getUsername());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("email already existed : {}", user.getUsername());
            throw new UserAlreadyExists(
                    "email already taken"
            );
        }
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {
            log.error("username already exists :{}", user.getUsername(), ex);
            throw new UserRegistrationException("username already exists");
        } catch (Exception ex) {
            log.error("unexpected error occurred", ex);
            throw new UserRegistrationException("unexpected error");
        }
    }

    @Override
    public String generateToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            return jwtUtil.generateToken(authRequest.getEmail());
        } catch (Exception e) {
            throw e;
        }
    }
}