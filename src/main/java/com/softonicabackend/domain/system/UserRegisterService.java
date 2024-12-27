package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class UserRegisterService implements UserRegistration {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserValidationChecker userValidation;
    private final HttpServletRequest httpServletRequest;

    @Value("${user.registration.success}")
    private String registrationSuccessMessage;


    @Override
    public UserRegisterResponseDto registerUser(UserRegisterDto userRegisterDto) {
        userValidation.validationProcessor(userRegisterDto);
        UserEntity userToSave = createUserObjectAndPasswordEncode(userRegisterDto);
        userRepository.save(userToSave);
        return new UserRegisterResponseDto(registrationSuccessMessage);
    }

    UserEntity createUserObjectAndPasswordEncode(UserRegisterDto userRegisterDto) {
        return new UserEntity(userRegisterDto.username(),
                userRegisterDto.email(),
                bCryptPasswordEncoder.encode(userRegisterDto.password()));
    }

    @Override
    @Transactional
    public void deleteLoggedUser(String username) {
        userRepository.deleteByUsername(username);
        forceLogout();
    }

    void forceLogout() {
        new SecurityContextLogoutHandler().logout(httpServletRequest,
                null, null);
    }
}
