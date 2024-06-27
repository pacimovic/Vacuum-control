package com.example.Backend.controllers;

import com.example.Backend.model.User;
import com.example.Backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_read_users")))
            return ResponseEntity.ok(userService.findAll());

        return ResponseEntity.status(403).build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_create_users")))
            return ResponseEntity.ok(userService.save(user));

        return ResponseEntity.status(403).build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_update_users")))
            return ResponseEntity.ok(userService.save(user));

        return ResponseEntity.status(403).build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_delete_users"))){
            userService.deleteById(userId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(403).build();
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> findUser(@PathVariable Long userId){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("can_read_users"))){
            return ResponseEntity.ok(userService.findById(userId));
        }

        return ResponseEntity.status(403).build();
    }

}
