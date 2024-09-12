package com.example.Backend.controllers;


import com.example.Backend.model.Permission;
import com.example.Backend.model.User;
import com.example.Backend.requests.LoginRequest;
import com.example.Backend.responses.LoginResponse;
import com.example.Backend.services.UserService;
import com.example.Backend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication au;
        try {
            au = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e){
            return ResponseEntity.status(401).build();
        }

        User user = this.userService.findByEmail(au.getName());

        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getEmail(), user)));
    }

}
