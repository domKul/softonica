package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final HttpServletRequest request;
    private final RedisTemplate<String, Boolean> redisTemplate;

    public void invalidateToken(boolean isDeleted, String username) {
        if (isDeleted) {
            Set<String> keys = redisTemplate.keys(getRedisKeyPatternForUser(username));
            if (keys != null) {
                redisTemplate.delete(keys);
            }
        }
        SecurityContextHolder.clearContext();
    }

    private String getBearerTokenFromRequest() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        String redisKey = getRedisKeyForToken(token);
        Boolean isValid = redisTemplate.opsForValue().get(redisKey);
        return Boolean.TRUE.equals(isValid); // Zwróć true tylko, jeśli wartość jest true
    }

    @Cacheable(cacheNames = "validTokens")
    public void addTokenToList(String token) {
        storeToken(token, true);
    }

    private String extractUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    private void storeToken(String token, boolean isValid) {
        String redisKey = getRedisKeyForToken(token);
        redisTemplate.opsForValue().set(redisKey, isValid, Duration.ofHours(8));
    }

    // Generowanie klucza Redis dla tokenu
    private String getRedisKeyForToken(String token) {
        String username = extractUsernameFromToken(token);
        return "token:" + username + ":" + token;
    }

    private String getRedisKeyPatternForUser(String username) {
        return "token:" + username + ":*";
    }
}
