package com.softonicabackend.infrastructure.validation;

import org.springframework.http.HttpStatus;

public record ErrorResponse (
        String message,
        HttpStatus httpStatus,
        String timestamp
){
}
