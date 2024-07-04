package com.example.Backend.controllers;

import com.example.Backend.model.User;
import com.example.Backend.services.ErrorService;
import com.example.Backend.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/errors")
public class ErrorController {

    private final ErrorService errorService;

    private final UserService userService;

    public ErrorController(ErrorService errorService, UserService userService) {
        this.errorService = errorService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllErrors(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(errorService.getAllErrors(user.getId()));
    }

}
