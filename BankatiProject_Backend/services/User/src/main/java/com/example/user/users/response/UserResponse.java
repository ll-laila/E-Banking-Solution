package com.example.user.users.response;

import com.example.user.users.entity.Role;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Role role
) {
}
