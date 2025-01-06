package com.softonicabackend.domain.system;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserLoginFailureService {

    private final UserRepository userRepository;

    public void incrementFailureCount(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            updateFailureCount(user);
            lockUserIfNecessary(user);
            userRepository.save(user);
        }
    }

    public void resetFailureCount(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.setFailures((byte) 0);
            userRepository.save(user);
        }
    }

    private void updateFailureCount(UserEntity user) {
        if (user.getFailures() < 4) {
            user.setFailures((byte) (user.getFailures() + 1));
        }
    }

    private void lockUserIfNecessary(UserEntity user) {
        if (user.getFailures() > 3) {
            user.setLocked(true);
        }
    }
}
