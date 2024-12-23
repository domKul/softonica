package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemFacade {

    private final UserRegistration userRegistration;

    public UserRegisterResponseDto register(UserRegisterDto userRegisterDto){
        return userRegistration.registerUser(userRegisterDto);
    }
}
