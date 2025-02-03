package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;
import com.softonicabackend.domain.system.exception.user.UserExceptionMessages;
import com.softonicabackend.domain.system.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
class UserFinderService implements UserFinder {

    private final UserRepository userRepository;
    private final UserValidationChecker userValidationChecker;

    @Override
    @Transactional
    public GetLoggedUserDto getLoggedUser(String login) {
        UserEntity userEntity = userRepository.findByUsernameOrEmail(login)
                .map(user->{
                    userValidationChecker.isUserDeleted(user.getDeleted());
                    return user;
                })
                .orElseThrow(() -> new UserNotFoundException(UserExceptionMessages.USER_NOT_FOUND.getMessage()));
        userEntity.setLastLogin(Instant.now().plus(1, ChronoUnit.HOURS));
        return UserMapper.mapToGetLoggedUserDto(userEntity);
    }

    @Override
    public boolean checkIfAccountIsLocked(String username) {
        UserEntity userEntity = findUserEntityByLogin(username);
        return userEntity.isLocked();
    }

    public String getEmailByUsername(String username) {
        return findUserEntityByLogin(username).getEmail();
    }

    private UserEntity findUserEntityByLogin(String login) {
        return userRepository.findByUsernameOrEmail(login)
                .orElseThrow(() -> new UserNotFoundException(
                        UserExceptionMessages.USER_NOT_FOUND.getMessage()));
    }
}
