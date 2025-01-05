package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import com.softonicabackend.domain.system.exception.user.UserAlreadyExistException;
import com.softonicabackend.domain.system.exception.user.UserExceptionMessages;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

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


}
