package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.UserDeleteDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final HttpServletRequest request;
    private final RedisTemplate<String, Boolean> redisTemplate;

    private void invalidateToken(boolean isDeleted, String username) {
        if (isDeleted) {
            Set<String> keys = redisTemplate.keys(getRedisKeyPatternForUser(username));
            if (keys != null) {
                redisTemplate.delete(keys);
            }
        }
        SecurityContextHolder.clearContext();
    }

    public void userSessionValidation(UserDeleteDto userDeleteDto, String loggedUser, SystemFacade systemFacade) {
        if(Objects.equals(getBearerTokenFromRequest(), userDeleteDto.token())){
            boolean deletedUser = systemFacade.deleteUserByUsername(loggedUser);
            invalidateToken(deletedUser, loggedUser);
        }
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
