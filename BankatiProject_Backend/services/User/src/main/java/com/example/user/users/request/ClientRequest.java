package com.example.user.users.request;

import java.util.Date;

public record ClientRequest(
        String id,
        String agentId,
        String firstName,
        String lastName,
        String email,
        String address,
        String cin,
        Date birthDate,
        String phoneNumber,
        String password,
        Boolean isFirstLogin,
        Date createdDate,
        String commercialRn,
        String patentNumber,
        Boolean isPaymentAccountActivated
) {
    // You can add validation methods if necessary, but it's optional
}

