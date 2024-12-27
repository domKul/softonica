package com.softonicabackend.infrastructure.system.user.controller;

import com.softonicabackend.domain.system.SystemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
class UserController {

    private final SystemFacade userFacade;

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteUser() {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        userFacade.deleteUserByUsername(loggedUser);
        return ResponseEntity.ok().build();
    }
}
