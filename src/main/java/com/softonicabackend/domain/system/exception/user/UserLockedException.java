package com.softonicabackend.domain.system.exception.user;

public class UserLockedException extends RuntimeException{
    public UserLockedException(String message) {
        super(message);
    }
}
