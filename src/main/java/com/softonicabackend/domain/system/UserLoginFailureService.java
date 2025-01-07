package com.softonicabackend.domain.system;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserLoginFailureService {

    private final UserRepository userRepository;

    @Transactional
    public void incrementFailureCount(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.incrementFailures();
            user.lockIfNecessary();
        }
    }

    @Transactional
    public void resetFailureCount(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.resetFailures();
        }
    }
}
