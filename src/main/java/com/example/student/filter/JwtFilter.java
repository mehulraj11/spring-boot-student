package com.example.student.filter;

import com.example.student.service.implementation.UserDetailsServiceImpl;
import com.example.student.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        String Token = null;
        String email = null;
        String role = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(Token);
                role = jwtUtil.extractRole(Token);
            } catch (JwtException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (!jwtUtil.isTokenExpired(Token)) {
                List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }
        filterChain.doFilter(request, response);
    }
}
