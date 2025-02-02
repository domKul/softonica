package com.softonicabackend.infrastructure.system.user.controller;

import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.domain.system.dto.UserDeleteDto;
import com.softonicabackend.infrastructure.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "User", description = "User management")
class UserController {

    private final SystemFacade userFacade;
    private final JwtTokenService jwtTokenService;

    @Operation(summary = "Delete user account", description = "required valid token and id")
    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteUser(@Valid @RequestBody UserDeleteDto userDeleteDto) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        jwtTokenService.userSessionValidation(userDeleteDto, loggedUser, userFacade);
        return ResponseEntity.ok().build();
    }
}
