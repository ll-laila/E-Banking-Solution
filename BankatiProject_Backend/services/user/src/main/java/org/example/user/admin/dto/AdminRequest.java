package org.example.user.admin.dto;

public record AdminRequest (
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String password
){}
