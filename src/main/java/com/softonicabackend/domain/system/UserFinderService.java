package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;
import com.softonicabackend.domain.system.exception.user.UserExceptionMessages;
import com.softonicabackend.domain.system.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
class UserFinderService implements UserFinder {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public GetLoggedUserDto getLoggedUser(String login) {
        UserEntity userEntity = userRepository.findByUsernameOrEmail(login)
                .orElseThrow(() -> new UserNotFoundException(UserExceptionMessages.USER_NOT_FOUND.getMessage()));
        userEntity.setLastlogon(Instant.now());
        return UserMapper.mapToGetLoggedUserDto(userEntity);
    }
}
