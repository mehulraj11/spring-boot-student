package com.example.student.config;

import com.example.student.entity.Permissions;
import com.example.student.entity.Role;
import com.example.student.filter.JwtFilter;
import com.example.student.service.implementation.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    LOGIN:
//    client -> Filter -> AuthenticationManager -> Provider -> UserDetailsService -> jwt generated -> client
//    Request:
//    client -> jwt filter -> token validates -> loads user -> security contex -> authorization -> controller
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register",
                                "/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/students/**").hasAuthority(Permissions.STUDENT_READ.name())
                        .requestMatchers(HttpMethod.POST,"/students/**").hasAuthority(Permissions.STUDENT_WRITE.name())
                        .requestMatchers(HttpMethod.DELETE,"/students/**").hasAuthority(Permissions.STUDENT_DELETE.name())
                        .requestMatchers(HttpMethod.PUT,"/students/**").hasAuthority(Permissions.STUDENT_WRITE.name())
                        .requestMatchers(HttpMethod.PATCH,"/students/**").hasAuthority(Permissions.STUDENT_WRITE.name())
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}