package com.example.user.virtualCardClient;

import java.time.LocalDateTime;

public record VirtualCardRequest(
        String userId,
        String cardNumber,
        LocalDateTime expirationDate,
        String status, // "ACTIVE", "INACTIVE", "EXPIRED"
        String montant
) {

}
