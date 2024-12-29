package com.softonicabackend.infrastructure.system.user.controller;

import com.softonicabackend.domain.system.dto.JwtResponseDto;
import com.softonicabackend.domain.system.dto.TokenRequestDto;
import com.softonicabackend.infrastructure.security.JwtAuthenticator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Login and generate token for Registered user")
class JwtTokenController {

    private final JwtAuthenticator jwtAuthenticator;

    @PostMapping("/login")
    ResponseEntity<JwtResponseDto> authAndGenerateToken(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        JwtResponseDto generateToken = jwtAuthenticator.authenticateAndGenerateToken(tokenRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(generateToken);
    }
}
