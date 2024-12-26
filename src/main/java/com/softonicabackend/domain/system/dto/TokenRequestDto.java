package com.softonicabackend.domain.system.dto;

public record TokenRequestDto(
        String login,
        String password
) {
}
