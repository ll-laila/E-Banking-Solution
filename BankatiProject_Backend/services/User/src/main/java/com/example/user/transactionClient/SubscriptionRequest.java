package com.example.user.transactionClient;

import java.math.BigDecimal;

public record SubscriptionRequest(
        String userId,
        String agentId,
        BigDecimal price,
        int durationInMonths
) {
}
