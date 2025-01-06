package com.softonicabackend.domain.system.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteDto(
        @NotBlank(message = "{user.username.not.null}")
        String username,
        @NotBlank(message = "{token.not.null}")
        String token) {
}
