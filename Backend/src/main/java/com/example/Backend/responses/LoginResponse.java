package com.example.Backend.responses;

import com.example.Backend.model.Permission;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class LoginResponse {

    private String jwt;

    private Permission permission;

    public LoginResponse(String jwt, Permission permission) {
        this.jwt = jwt;
        this.permission = permission;
    }
}
