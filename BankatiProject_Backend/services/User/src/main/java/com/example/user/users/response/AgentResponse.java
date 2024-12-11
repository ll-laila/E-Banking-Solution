package com.example.user.users.response;

import lombok.Builder;

import java.util.Date;


public record AgentResponse (
        String id,
        String firstName,
        String lastName,
        String email,
        String address,
        String cin,
        Date birthDate ,
        String commercialRn,
        String image,
        String password,
        String phoneNumber,
        boolean isFirstLogin,
        String currency
){}
