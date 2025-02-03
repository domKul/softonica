package com.softonicabackend.domain.system;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SystemTestConfiguration {

    private final InMemoryUserRepository inMemoryUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SystemTestConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.inMemoryUserRepository = new InMemoryUserRepository();
    }

    SystemFacade systemFacade(){
        return new SystemFacade(new UserRegisterService(inMemoryUserRepository,passwordEncoder,
                new UserValidationChecker(inMemoryUserRepository))
                ,new UserFinderService(inMemoryUserRepository, new UserValidationChecker(inMemoryUserRepository))
                ,new UserLoginFailureService(inMemoryUserRepository));
    }
}
