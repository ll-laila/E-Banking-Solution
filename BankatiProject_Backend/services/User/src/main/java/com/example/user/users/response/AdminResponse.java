package com.example.user.users.response;


public record AdminResponse (
        String id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String password
){}
