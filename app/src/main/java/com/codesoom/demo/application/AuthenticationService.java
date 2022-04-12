package com.codesoom.demo.application;

import com.codesoom.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private JwtUtil jwtUtil;

    public AuthenticationService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {
        return jwtUtil.encode(1L);
    }

    public Long parseToken(String accessToken) {
        Claims claims = jwtUtil.decode(accessToken);
        return claims.get("userId", Long.class);
    }
}
