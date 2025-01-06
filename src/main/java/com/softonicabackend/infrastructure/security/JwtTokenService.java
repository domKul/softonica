package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public boolean isTokenValid(String token) {
        String redisKey = getRedisKeyForToken(token);
        Boolean isValid = redisTemplate.opsForValue().get(redisKey);
        return Boolean.TRUE.equals(isValid);
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

    private String getRedisKeyForToken(String token) {
        String username = extractUsernameFromToken(token);
        return "token:" + username + ":" + token;
    }

    private String getRedisKeyPatternForUser(String username) {
        return "token:" + username + ":*";
    }
}
