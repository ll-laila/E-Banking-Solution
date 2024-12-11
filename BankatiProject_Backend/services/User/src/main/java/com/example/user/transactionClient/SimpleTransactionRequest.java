package com.example.user.transactionClient;

import java.math.BigDecimal;

public record SimpleTransactionRequest(
        String senderId,
        String beneficiaryId,
        BigDecimal amount,
        TransactionType transactionType
) {
}
