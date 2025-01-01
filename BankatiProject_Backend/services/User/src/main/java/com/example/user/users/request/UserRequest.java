package com.example.user.users.request;

import com.example.user.users.entity.Role;

public record UserRequest(
         String id,
         String password,
         String firstName,
         String lastName,
         String email,
         String phoneNumber,
         String token,
         Role role
) {
}
