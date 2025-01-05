package com.softonicabackend.infrastructure.system.user.controller;

import com.softonicabackend.domain.system.SystemFacade;
import com.softonicabackend.infrastructure.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
class UserController {

    private final SystemFacade userFacade;
    private final JwtTokenService jwtTokenService;

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteUser() {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean deletedUser = userFacade.deleteUserByUsername(loggedUser);
        jwtTokenService.invalidateToken(deletedUser,loggedUser);
        return ResponseEntity.ok().build();
    }
}
