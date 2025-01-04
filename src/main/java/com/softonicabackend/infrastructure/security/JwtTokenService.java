package com.softonicabackend.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final HttpServletRequest request;
    private final ConcurrentHashMap<String, Boolean> invalidatedTokens = new ConcurrentHashMap<>();

    public void invalidateToken() {
        String bearerTokenFromRequest = getBearerTokenFromRequest();
        invalidatedTokens.put(bearerTokenFromRequest, false);
    }

    public boolean isTokenValid(String token) {
        return invalidatedTokens.getOrDefault(token, true);
    }

    private String getBearerTokenFromRequest() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
