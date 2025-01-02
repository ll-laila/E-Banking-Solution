package com.example.user.users.response;

import com.example.user.users.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class AuthResponse {
    private String token;
    private Set<Role> roles;
}