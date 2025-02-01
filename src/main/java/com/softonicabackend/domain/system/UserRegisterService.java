package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
class UserRegisterService implements UserRegistration {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserValidationChecker userValidation;

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
    public boolean deleteLoggedUser(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (userRepository.countUserLinks(username) == 1)  {
                userRepository.deleteByUsername(username);
            } else {
                user.setDeleted(LocalDateTime.now());
            }
            return true;
        }
        return false;
    }
}
