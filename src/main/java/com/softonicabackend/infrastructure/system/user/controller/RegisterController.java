package com.softonicabackend.infrastructure.user.controller;


import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.UserRegisterDto;
import com.softonicabackend.domain.system.dto.UserRegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@CrossOrigin("*")
class RegisterController {

    private final SystemFacade systemFacade;

    @PostMapping("/register")
    ResponseEntity<UserRegisterResponseDto> register(@RequestBody  @Valid UserRegisterDto registerUserDto) {
        UserRegisterResponseDto registerResult = systemFacade.register(
                new UserRegisterDto(registerUserDto.username(), registerUserDto.password(),
                        registerUserDto.confirmPassword(), registerUserDto.email(),
                        registerUserDto.termsAccepted(),
                        registerUserDto.privacyPolicyAccepted()));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }
}