package org.example.user.admin.dto;

public record AdminRequest (
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String password
){}
