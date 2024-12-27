package com.softonicabackend.domain.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class SystemBeanConfiguration {
    @Bean
    SystemFacade systemFacade(UserRegistration userRegistration,UserFinder userFinder) {
        return new SystemFacade(userRegistration, userFinder);
    }

    @Bean
    UserValidationChecker userValidationChecker(UserRepository userRepository) {
        return new UserValidationChecker(userRepository);
    }

    @Bean
    UserRegisterService userRegisterService(UserRepository userRepository,
                                            UserValidationChecker userValidationChecker) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new UserRegisterService(userRepository, passwordEncoder, userValidationChecker);
    }

    @Bean
    UserFinderService userFinderService(UserRepository userRepository) {
        return new UserFinderService(userRepository);
    }
}
