package com.example.student.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {
    private static final int MAX_REQUESTS = 50;
    private static final long WINDOW_TIME = 60 * 1000;

    private final Map<String, Integer> requestCountMap = new ConcurrentHashMap<>();
    private final Map<String, Long> requestTimeMap = new ConcurrentHashMap<>();


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        String ip = request.getRemoteAddr();
        Long currentTime = System.currentTimeMillis();

        requestCountMap.putIfAbsent(ip, 0);
        requestTimeMap.putIfAbsent(ip, currentTime);

        Long windowStartTime = requestTimeMap.get(ip);

        if (currentTime - windowStartTime > WINDOW_TIME) {
            requestCountMap.put(ip, 0);
            requestTimeMap.put(ip, currentTime);
        }

        int count = requestCountMap.get(ip) + 1;
        requestCountMap.put(ip, count);

        if (count > MAX_REQUESTS) {
            response.setStatus(429);
            response.getWriter().write("Too many requests. Try again later.");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
