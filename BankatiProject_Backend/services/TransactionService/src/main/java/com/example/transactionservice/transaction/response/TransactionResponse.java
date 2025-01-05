package com.example.transactionservice.transaction.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse (
        String id,
        BigDecimal amount,
        String transactionMethod,
        String beneficiaryId,
        String beneficiaryName,
        String beneficiaryPhone,
        String beneficiaryRole,
        String transactionType,
        String status,
        String senderCurrency,
        String beneficiaryCurrency,
        LocalDateTime createdDate,
        String senderId,
        String senderName,
        String senderPhoneNumber,
        String senderRole,
        LocalDateTime lastModifiedDate,
        LocalDateTime validatedDate
){
}

