package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final HttpServletRequest request;
    private final ConcurrentHashMap<String, Boolean> tokenList = new ConcurrentHashMap<>();

    public void invalidateToken(boolean isDeleted, String username) {
        if (isDeleted) {
            String bearerTokenFromRequest = getBearerTokenFromRequest();
            if (bearerTokenFromRequest != null) {
                tokenList.put(bearerTokenFromRequest, false);
                tokenList.entrySet().stream()
                        .filter(entry -> username.equals(extractUsernameFromToken(entry.getKey())))
                        .forEach(entry -> tokenList.put(entry.getKey(), false));
            }
            SecurityContextHolder.clearContext();
        }
    }

    public boolean isTokenValid(String token) {
        return tokenList.getOrDefault(token, true);
    }

    public void addTokenToList(String token) {
        tokenList.put(token, true);
    }

    private String getBearerTokenFromRequest() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private String extractUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
}
