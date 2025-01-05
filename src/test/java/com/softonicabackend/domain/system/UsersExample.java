package com.softonicabackend.domain.system;

import com.softonicabackend.domain.system.dto.UserRegisterDto;

public interface UsersExample {

    default UserRegisterDto user1WithCorrectBody(){
        return new UserRegisterDto("username",
                "Password1","Password1","1user@gmail.com",
                true, true);
    }
    default UserRegisterDto user1WithSameEmail(){
        return new UserRegisterDto("1username",
                "Password1","Password1","1user@gmail.com",
                true, true);
    }
    default UserRegisterDto user1WithDifferentPassword(){
        return new UserRegisterDto("username",
                "Password1","Password12","1user@gmail.com",
                true, true);
    }
    default UserRegisterDto user1WithWrongPasswordFormat(){
        return new UserRegisterDto("username",
                "Password","Password","1user@gmail.com",
                true, true);
    }
}
