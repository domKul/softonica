package com.softonicabackend.domain.system.dto;

import jakarta.validation.constraints.*;

public record UserRegisterDto(
        @NotBlank(message = "{user.username.not.null}")
        @Pattern(
                regexp = "^(?!.*\\b(admin|administrator|root|system|mod|" +
                        "moderator|support|superuser|ceo|cto|developer|founder)\\b)[a-zA-Z0-9]+$",
                message = "{user.name.validation}"
        )
        String username,
        @Size(min = 6, message = "{password.size}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$",
                message = "{password.pattern}")
        String password,
        @Size(min = 6, message = "{password.size}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$",
                message = "{password.pattern}")
        String confirmPassword,
        @Email(message = "{email.format}")
        String email,
        @AssertTrue
        boolean termsAccepted,
        @AssertTrue
        boolean privacyPolicyAccepted
) {
}