package com.example.transactionservice.transaction.request;

import com.example.transactionservice.transaction.entity.TransactionMethod;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import com.example.transactionservice.transaction.entity.TransactionType;
import com.example.transactionservice.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        String id,
        BigDecimal amount, // Montant de la transaction
        TransactionMethod transactionMethod, // Méthode de transaction (ex : CREDIT_CARD, BANK_TRANSFER)
        Integer beneficiaryId, // Identifiant du bénéficiaire
        TransactionType transactionType, // Type de transaction (ex : PAYMENT, TRANSFER)
        TransactionStatus status, // Statut initial de la transaction
        String currency, // Devise (EUR, USD, MAD)
        String externalReference, // Référence du système externe
        LocalDateTime validatedDate,// Date de validation, si applicable
        User user
){
}