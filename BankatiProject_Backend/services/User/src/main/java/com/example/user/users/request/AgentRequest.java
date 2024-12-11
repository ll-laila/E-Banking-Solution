package com.example.user.users.request;

import java.util.Date;

public record AgentRequest (
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
){
}

