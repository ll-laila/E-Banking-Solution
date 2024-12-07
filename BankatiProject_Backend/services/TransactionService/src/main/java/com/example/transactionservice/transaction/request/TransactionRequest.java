package com.example.transactionservice.transaction.request;

import com.example.transactionservice.transaction.entity.TransactionMethod;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import com.example.transactionservice.transaction.entity.TransactionType;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        String id,
        BigDecimal amount, // Montant de la transaction
        TransactionMethod transactionMethod, // Méthode de transaction (ex : CREDIT_CARD, BANK_TRANSFER)
        String beneficiaryId,
        String beneficiaryName,
        String beneficiaryPhone,
        String beneficiaryRole,
        TransactionType transactionType, // Type de transaction (ex : PAYMENT, TRANSFER)
        TransactionStatus status, // Statut initial de la transaction
        String currency, // Devise (EUR, USD, MAD)
        LocalDateTime validatedDate,// Date de validation, si applicable
        String senderId,
        String senderName,
        String senderPhoneNumber,
        String senderRole
){
}