package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.JwtResponseDto;
import com.softonicabackend.domain.system.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties properties;
    private final SystemFacade systemFacade;

    public JwtResponseDto authenticateAndGenerateToken(TokenRequestDto tokenRequestDto) {
        checkAccountLockStatus(tokenRequestDto.login());
        try {
            Authentication authentication = authenticateUser(tokenRequestDto);
            User user = (User) authentication.getPrincipal();
            String token = createToken(user);
            systemFacade.resetFailureCount(tokenRequestDto.login());
            return buildJwtResponse(token, user.getUsername());
        } catch (BadCredentialsException e) {
            handleAuthenticationFailure(tokenRequestDto.login());
            throw new BadCredentialsException("Invalid login credentials", e);
        }
    }

    private void checkAccountLockStatus(String login) {
        systemFacade.checkIfAccountIsLocked(login);
    }

    private Authentication authenticateUser(TokenRequestDto tokenRequestDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequestDto.login(), tokenRequestDto.password()));
    }

    private String createToken(User user) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofHours(properties.expirationTimeInHours()));
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
    }

    private JwtResponseDto buildJwtResponse(String token, String username) {
        return JwtResponseDto.builder()
                .token(token)
                .username(username)
                .build();
    }

    private void handleAuthenticationFailure(String login) {
        systemFacade.incrementFailureCount(login);
    }
}