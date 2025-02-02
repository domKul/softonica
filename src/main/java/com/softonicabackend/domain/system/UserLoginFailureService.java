package com.softonicabackend.domain.system;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
class UserLoginFailureService {

    private final UserRepository userRepository;

    @Transactional
    public void incrementFailureCount(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.incrementFailures();
            user.lockIfNecessary();
        });
    }

    @Transactional
    public void resetFailureCount(String username) {
        userRepository.findByUsername(username).ifPresent(UserEntity::resetFailures);
    }
}
