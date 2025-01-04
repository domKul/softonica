package com.softonicabackend.infrastructure.security;

import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.GetLoggedUserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SystemFacade systemFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GetLoggedUserDto userByUsername;
        try {
            userByUsername = systemFacade.findUserByUsername(username);
            if(userByUsername.deleted() != null) {
                throw new BadCredentialsException("Username " + username + " not found");
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("user not found");
        }
        return getUser(userByUsername);
    }

    private User getUser(GetLoggedUserDto userDto) {
        return new User(
                userDto.username(),
                userDto.password(),
                Collections.emptyList());
    }
}
