package com.softonicabackend.infrastructure.security;

import com.softonicabackend.domain.system.SystemFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemFacade systemFacade;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {return null;
    }
}
