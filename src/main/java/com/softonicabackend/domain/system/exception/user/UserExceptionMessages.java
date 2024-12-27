package com.softonicabackend.domain.system.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserExceptionMessages {

    EMAIL_ALREADY_TAKEN("Email already taken"),
    USER_WITH_GIVEN_USERNAME_ALREADY_EXIST("User with given username already exists"),
    PRIVACY_POLICY_AND_TERMS_REQUIRED("privacy Policy And Terms Required"),
    DIFFERENT_PASSWORD("different password");
    private final String message;
}
