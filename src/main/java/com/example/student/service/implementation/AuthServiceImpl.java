package com.example.student.service.implementation;

import com.example.student.dto.LocalStorageDTO;
import com.example.student.entity.AuthRequest;
import com.example.student.entity.Users;
import com.example.student.exception.UserAlreadyExists;
import com.example.student.exception.UserRegistrationException;
import com.example.student.exception.WrongPasswordException;
import com.example.student.repository.UserRepository;
import com.example.student.service.AuthService;
import com.example.student.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            log.error("email already existed : {}", user.getEmail());
            throw new UserAlreadyExists("email already taken");
        }
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            log.info("{} has been registered", user.getEmail());
            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {
            log.error("Error: {}", ex.getMessage());
            throw new UserRegistrationException(ex.getMessage());
        } catch (Exception ex) {
            log.error("unexpected error occurred :{}", ex.getMessage());
            throw new UserRegistrationException(ex.getMessage());
        }
    }

    @Override
    public LocalStorageDTO createToken(AuthRequest authRequest) {

        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        }catch (Exception e){
            log.error("wrong password by user {}", authRequest.getEmail());
            throw new WrongPasswordException("wrong password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = authentication.getName();
//        System.out.println(authentication.getAuthorities().toArray()[0]);
        String role = authentication.getAuthorities().toArray()[0].toString();
//        System.out.println(role);
//        System.out.println(email);
        log.info("{} has generated login token", email);

        String token = jwtUtil.generateToken(email, role);
        return new LocalStorageDTO(token, role);
    }
}