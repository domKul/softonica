package com.softonicabackend.domain.system.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteDto(
        @NotBlank(message = "{token.not.null}")
        String token) {
}
