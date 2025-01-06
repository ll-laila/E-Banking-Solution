package com.example.user.virtualCardClient;

import java.time.LocalDateTime;

public record VirtualCardResponse(
        String id,
        String cardNumber,
        String userId,
        LocalDateTime expirationDate,
        String status, // "ACTIVE", "INACTIVE", "EXPIRED"
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        double montant
) {
}
