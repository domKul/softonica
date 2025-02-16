package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;
import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import com.softonicabackend.domain.system.exception.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SystemFacadeTest implements UsersExample {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    SystemFacade systemFacade = new SystemTestConfiguration(passwordEncoder).systemFacade();

    @Test
    void shouldRegisterUserSuccessfully() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        //When
        UserRegisterResponseDto register = systemFacade.register(user);
        //Then
        assertAll(
                () -> assertThat(register).isNotNull(),
                () -> assertThat(user.username()).isEqualTo("username"),
                () -> assertThat(user.email()).isEqualTo("1user@gmail.com")
        );
    }

    @Test
    void shouldThrowUserAlreadyExistExceptionWhenTryingToRegisterUserWithSameUsername() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        //When
        UserAlreadyExistException userAlreadyExistException = assertThrows(UserAlreadyExistException.class,
                () -> systemFacade.register(user));
        //Then
        assertThat(userAlreadyExistException.getMessage())
                .isEqualTo(UserExceptionMessages.USER_WITH_GIVEN_USERNAME_ALREADY_EXIST.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTryingToRegisterUserWithExistingEmail() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        //When
        UserAlreadyExistException userAlreadyExistException = assertThrows(UserAlreadyExistException.class,
                () -> systemFacade.register(user1WithSameEmail()));
        //Then
        assertThat(userAlreadyExistException.getMessage())
                .isEqualTo(UserExceptionMessages.EMAIL_ALREADY_TAKEN.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTermsAreNotValid() {
        //Given
        UserRegisterDto user = user1WithoutTermsAccepted();
        //When
        PrivacyPolicyAndTermsException privacyPolicyAndTermsException = assertThrows(PrivacyPolicyAndTermsException.class,
                () -> systemFacade.register(user));
        //Then
        assertThat(privacyPolicyAndTermsException.getMessage())
                .isEqualTo(UserExceptionMessages.PRIVACY_POLICY_AND_TERMS_REQUIRED.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsDifferentThenConfirmPassword() {
        //Given
        UserRegisterDto user = user1WithDifferentPasswords();
        //When
        DifferentPasswordException differentPasswordException = assertThrows(DifferentPasswordException.class,
                () -> systemFacade.register(user));
        //Then
        assertThat(differentPasswordException.getMessage())
                .isEqualTo(UserExceptionMessages.DIFFERENT_PASSWORD.getMessage());
    }

    @Test
    void shouldGetUserByUsernameSuccessfully() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        //When
        GetLoggedUserDto userByUsername = systemFacade.findUserByUsername(user.username());

        //Then
        assertAll(
                () -> assertThat(user.username()).isEqualTo(userByUsername.username()),
                () -> assertThat(passwordEncoder.matches(user.password(), userByUsername.password())).isTrue()
        );
    }

    @Test
    void shouldThrowExceptionWhenTryingToFindUserByUsernameWithWrongUsername() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        //When
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> systemFacade.findUserByUsername("wrongUsername"));
        //Then
        assertThat(userNotFoundException.getMessage())
                .isEqualTo(UserExceptionMessages.USER_NOT_FOUND.getMessage());

    }

    @Test
    void shouldCheckIfUserIsLockedOnNot() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        //When
        boolean accountCheck = systemFacade.checkIfAccountIsLocked(user.username());
        //Then
        assertFalse(accountCheck);
    }

    @Test
    void shouldIncrementFailureCounter() {
        // Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        assertFalse(systemFacade.checkIfAccountIsLocked(user.username()));
        // When
        systemFacade.incrementFailureCount(user.username());
        systemFacade.incrementFailureCount(user.username());
        systemFacade.incrementFailureCount(user.username());
        // Then
        boolean accountCheck = systemFacade.checkIfAccountIsLocked(user.username());
        assertTrue(accountCheck, "Account should be locked after 3 failed attempts");
    }

    @Test
    void shouldResetFailureCounter() {
        //Given
        UserRegisterDto user = user1WithCorrectBody();
        systemFacade.register(user);
        systemFacade.incrementFailureCount(user.username());
        systemFacade.incrementFailureCount(user.username());
        systemFacade.incrementFailureCount(user.username());
        boolean accountBeforeRest = systemFacade.checkIfAccountIsLocked(user.username());
        //When
        systemFacade.resetFailureCount(user.username());
        //Then
        boolean accountAfterReset = systemFacade.checkIfAccountIsLocked(user.username());
        assertAll(
                ()-> assertTrue(accountBeforeRest, "Account should be locked after 3 failed attempts"),
                ()-> assertFalse(accountAfterReset, "Account should be unlocked")

        );
    }

}
