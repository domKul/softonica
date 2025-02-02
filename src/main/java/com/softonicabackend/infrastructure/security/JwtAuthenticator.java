package com.softonicabackend.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.softonicabackend.domain.system.dto.JwtResponseDto;
import com.softonicabackend.domain.system.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final JwtTokenService jwtTokenService;

    public JwtResponseDto authenticateAndGenerateToken(TokenRequestDto tokenRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequestDto.login(),tokenRequestDto.password()));
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();
        return JwtResponseDto.builder()
                .token(token)
                .username(username)
                .build();
    public JwtResponseDto authenticateAndGenerateToken(TokenRequestDto tokenRequestDto) {
        if (checkAccountLockStatus(tokenRequestDto.login())) {
            String emailByUsername = systemFacade.getEmailByUsername(tokenRequestDto.login());
            sendAccountLockedEmail(emailByUsername);
            throw new UserLockedException(UserExceptionMessages.ACCOUNT_LOCKED.getMessage());
        }
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

    private boolean checkAccountLockStatus(String login) {
        return systemFacade.checkIfAccountIsLocked(login);
    }

    private Authentication authenticateUser(TokenRequestDto tokenRequestDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequestDto.login(), tokenRequestDto.password()));
    }

    private String createToken(User user) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofDays(properties.expirationDays()));
        String issuer = properties.issuer();
        String jwt = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
        token.invalidateToken();
        return jwt;
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

    private void sendAccountLockedEmail(String email) {
        String accountLockedMessage = "Your account is locked due to multiple failed login attempts.";
        notifyObservers(accountLockedMessage, email);
    }
}