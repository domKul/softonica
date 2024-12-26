package com.softonicabackend.domain.system.exception;

public class DifferentPasswordException extends RuntimeException {

    public DifferentPasswordException(String message) {
        super(message);
    }
}
