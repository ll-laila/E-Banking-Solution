package com.example.user.users.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record AgentResponse (
        String id,
        String firstName,
        String lastName,
        String email,
        String address,
        String cin,
        Date birthDate ,
        String password,
        String phoneNumber
){}