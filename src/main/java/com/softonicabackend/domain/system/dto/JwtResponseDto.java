package com.softonicabackend.domain.system.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token) {
}
