package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.GetLoggedUserDto;

class UserMapper {

    static GetLoggedUserDto mapToGetLoggedUserDto(UserEntity user){
        return new GetLoggedUserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getDeleted()
        );
    }
}