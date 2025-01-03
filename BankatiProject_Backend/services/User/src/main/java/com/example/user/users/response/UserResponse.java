package com.example.user.users.response;

import com.example.user.users.entity.Role;

import java.util.Date;

public record UserResponse(
        String id,
        String agentId,
        String firstName,
        String lastName,
        String email,
        String address,
        String cin,
        Date birthDate ,
        String phoneNumber,

        Role role,
        String password,
        boolean isFirstLogin ,
        String commercialRn,
        String image,
        String patentNumber ,
        Boolean isPaymentAccountActivated ,
        String typeHissab,
        String currency

) {
}
