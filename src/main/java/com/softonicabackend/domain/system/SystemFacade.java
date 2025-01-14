package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;
import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemFacade {

    private final UserRegistration userRegistration;
    private final UserFinder userFinderService;
    private final UserLoginFailureService userLoginFailureService;


    public UserRegisterResponseDto register(UserRegisterDto userRegisterDto) {
        return userRegistration.registerUser(userRegisterDto);
    }

    public GetLoggedUserDto findUserByUsername(String username) {
        return userFinderService.getLoggedUser(username);
    }

    public boolean checkIfAccountIsLocked(String username) {
        return userFinderService.checkIfAccountIsLocked(username);
    }

    public void incrementFailureCount(String username) {
        userLoginFailureService.incrementFailureCount(username);
    }

    public void resetFailureCount(String username) {
        userLoginFailureService.resetFailureCount(username);
    }

    public String getEmailByUsername(String username) {
        return userFinderService.getEmailByUsername(username);
    }
}
