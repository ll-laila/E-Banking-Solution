package com.example.user.users.response;

import java.util.Date;

public record ClientResponse(
        String id,
        String agentId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Date createdDate
) {
}
