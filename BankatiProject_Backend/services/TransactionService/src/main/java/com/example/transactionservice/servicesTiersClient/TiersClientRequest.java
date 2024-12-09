package com.example.transactionservice.servicesTiersClient;

import java.math.BigDecimal;

public record TiersClientRequest(
        String beneficiaryId,
        String senderId,
        BigDecimal amount,
        String beneficiaryCurrency,
        String senderCurrency
) {
}
