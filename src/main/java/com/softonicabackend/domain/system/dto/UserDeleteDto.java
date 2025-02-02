package com.softonicabackend.domain.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDeleteDto(
        @NotNull(message = "{id.not.null}")
        Long id,
        @NotBlank(message = "{token.not.null}")
        String token) {
}
