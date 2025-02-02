package com.softonicabackend.infrastructure.configs;

import com.softonicabackend.infrastructure.email.LockEmailNotifier;
import com.softonicabackend.infrastructure.security.JwtAuthenticator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailObserverConfig {

    public EmailObserverConfig(JwtAuthenticator jwtAuthenticator, LockEmailNotifier lockEmailNotifier) {
        jwtAuthenticator.attach(lockEmailNotifier);
    }
}
