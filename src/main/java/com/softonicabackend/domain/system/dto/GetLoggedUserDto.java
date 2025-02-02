package com.softonicabackend.domain.system.dto;

import java.time.LocalDateTime;

public record GetLoggedUserDto(
        Long id,
        String username,
        String password,
        LocalDateTime deleted
){
}
