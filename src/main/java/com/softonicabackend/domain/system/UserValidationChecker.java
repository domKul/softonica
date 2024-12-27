package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.exception.user.DifferentPasswordException;
import com.softonicabackend.domain.system.exception.user.PrivacyPolicyAndTermsException;
import com.softonicabackend.domain.system.exception.user.UserAlreadyExistException;
import com.softonicabackend.domain.system.exception.user.UserExceptionMessages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserValidationChecker implements UserValidation {

    private final UserRepository userRepository;

    void validationProcessor(UserRegisterDto userRegisterDto) {
        validateUsernameAndEmailUniqueness(userRegisterDto.username(), userRegisterDto.email());
        passwordConfirmation(userRegisterDto.password(), userRegisterDto.confirmPassword());
        isUsernameExist(userRegisterDto.username());
        isEmailExist(userRegisterDto.email());
        validPrivacyAndTerms(userRegisterDto.privacyPolicyAccepted(), userRegisterDto.termsAccepted());
    }

    @Override
    public void validateUsernameAndEmailUniqueness(String username, String email) {
        isUsernameExist(username);
        isEmailExist(email);
    }

    @Override
    public void validPrivacyAndTerms(boolean privacyPolicy, boolean terms) {
        if (!privacyPolicy || !terms) {
            throw new PrivacyPolicyAndTermsException(UserExceptionMessages.PRIVACY_POLICY_AND_TERMS_REQUIRED.getMessage());
        }
    }

    @Override
    public void passwordConfirmation(String passwordConfirmation, String password) {
        if (!passwordConfirmation.equals(password)) {
            throw new DifferentPasswordException(UserExceptionMessages.DIFFERENT_PASSWORD.getMessage());
        }
    }

    @Override
    public void isUsernameExist(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(UserExceptionMessages.
                    USER_WITH_GIVEN_USERNAME_ALREADY_EXIST.getMessage());
        }
    }

    @Override
    public void isEmailExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException(UserExceptionMessages.
                    EMAIL_ALREADY_TAKEN.getMessage());
        }
    }
}