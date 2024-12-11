package com.example.transactionservice.servicesTiersClient;

import java.math.BigDecimal;

public record TiersClientRequest(
        String senderCurrency,
        String beneficiaryCurrency,
        BigDecimal amount,
        String senderId,
        String beneficiaryId



) {
}
