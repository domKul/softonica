package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;

interface UserRegistration {
    UserRegisterResponseDto registerUser(UserRegisterDto userRegisterDto);
}