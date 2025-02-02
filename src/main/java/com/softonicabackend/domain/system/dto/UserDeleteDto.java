package com.softonicabackend.domain.system.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteDto(
        @NotBlank(message = "{id.not.null}")
        Long id,
        @NotBlank(message = "{token.not.null}")
        String token) {
}
