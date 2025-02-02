package com.softonicabackend.infrastructure.validation;

import com.softonicabackend.domain.system.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                localDateTime.format(formatter)
        );
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(DifferentPasswordException.class)
    public ResponseEntity<ErrorResponse> handleDifferentPasswordException(DifferentPasswordException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                localDateTime.format(formatter)
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PrivacyPolicyAndTermsException.class)
    public ResponseEntity<ErrorResponse> handlePrivacyPolicyAndTermsException(PrivacyPolicyAndTermsException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                localDateTime.format(formatter)
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String>errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                localDateTime.format(formatter)
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<ErrorResponse> handleUserLockedException(UserLockedException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.CONFLICT,
                localDateTime.format(formatter)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        ErrorResponse errors = new ErrorResponse(
                e.getMessage(),
                HttpStatus.CONFLICT,
                localDateTime.format(formatter)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}