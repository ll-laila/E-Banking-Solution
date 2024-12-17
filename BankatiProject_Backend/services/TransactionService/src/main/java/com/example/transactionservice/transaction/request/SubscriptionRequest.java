package com.example.transactionservice.transaction.request;

import java.math.BigDecimal;

public record SubscriptionRequest(
        String userId,
        String agentId,
        BigDecimal price,
        int durationInMonths
) {
}
