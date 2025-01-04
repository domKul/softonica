package com.softonicabackend.domain.system.exception.user;

public class UserDeletedException extends RuntimeException {
    public UserDeletedException(String message) {
        super(message);
    }
}
