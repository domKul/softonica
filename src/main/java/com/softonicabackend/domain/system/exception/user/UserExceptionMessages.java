package com.softonicabackend.domain.system.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserExceptionMessages {

    USER_NOT_FOUND("User not found"),
    USER_DELETED("User was deleted"),
    EMAIL_ALREADY_TAKEN("Email already taken"),
    USER_WITH_GIVEN_USERNAME_ALREADY_EXIST("User with given username already exists"),
    PRIVACY_POLICY_AND_TERMS_REQUIRED("privacy Policy And Terms Required"),
    DIFFERENT_PASSWORD("different password"),
    ACCOUNT_LOCKED("account locked");
    private final String message;
}
