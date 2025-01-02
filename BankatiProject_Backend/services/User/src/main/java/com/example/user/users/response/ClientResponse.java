package com.example.user.users.response;

import java.util.Date;

public record ClientResponse(
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
        Boolean isPaymentAccountActivated,
        String typeHissab,
        String currency
) {
}
