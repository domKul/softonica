package com.softonicabackend.infrastructure.system.user.controller;


import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Register", description = "This endpoint allowing " +
        "you to register an account")
class RegisterController {

    private final SystemFacade systemFacade;

    @PostMapping("/register")
    ResponseEntity<UserRegisterResponseDto> register(@RequestBody  @Valid UserRegisterDto registerUserDto) {
        UserRegisterResponseDto registerResult = systemFacade.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }
}