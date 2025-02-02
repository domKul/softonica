package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;

interface UserFinder {

    GetLoggedUserDto getLoggedUser(String username);

    boolean checkIfAccountIsLocked(String username);

    String getEmailByUsername(String username);
}