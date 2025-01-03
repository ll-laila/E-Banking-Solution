package com.example.user.transactionClient;

import com.example.user.users.entity.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        String id,
        BigDecimal amount, // Montant de la transaction
        String beneficiaryId,
        String beneficiaryName,
        String beneficiaryPhone,
        Role beneficiaryRole,
        TransactionType transactionType, // Type de transaction (ex : PAYMENT, TRANSFER)
        TransactionStatus status, // Statut initial de la transaction
        String beneficiaryCurrency, // Devise (EUR, USD, MAD)
        LocalDateTime validatedDate,// Date de validation, si applicable
        String senderId,
        String senderName,
        String senderPhoneNumber,
        Role senderRole,
        String senderCurrency
) {
}
