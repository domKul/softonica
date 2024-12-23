package com.softonicabackend.domain.system;

interface UserValidation {
    void isEmailExist(String email);
    void isUsernameExist(String username);
    void passwordConfirmation(String passwordConfirmation, String password);
    void validateUsernameAndEmailUniqueness(String username, String email);
    void validPrivacyAndTerms(boolean privacy, boolean terms);
}
