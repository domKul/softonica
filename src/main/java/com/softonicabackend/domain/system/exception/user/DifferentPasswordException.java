package com.softonicabackend.domain.system.exception.user;

public class DifferentPasswordException extends RuntimeException {

    public DifferentPasswordException(String message) {
        super(message);
    }
}
