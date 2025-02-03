package com.softonicabackend.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "jwt")
public record JwtConfigurationProperties(
        String secret,
        int expirationTimeInHours,
        String issuer
) {

}