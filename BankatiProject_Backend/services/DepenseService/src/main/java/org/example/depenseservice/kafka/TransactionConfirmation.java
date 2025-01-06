package org.example.depenseservice.kafka;


import org.example.depenseservice.kafka.transaction.TransactionMethod;
import org.example.depenseservice.kafka.transaction.TransactionStatus;
import org.example.depenseservice.kafka.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionConfirmation(
        String transactionId, // ID unique de la transaction
        BigDecimal amount, // Montant
        TransactionMethod transactionMethod, // Méthode de transaction
        TransactionType transactionType, // Type de transaction (paiement, transfert, etc.)
        TransactionStatus transactionStatus, // Statut de la transaction
        String currency, // Devise utilisée
        String userFirstname, // Prénom de l'utilisateur
        String userLastname, // Nom de l'utilisateur
        String userEmail, // Email de l'utilisateur
        String userPhone, // Numéro de téléphone de l'utilisateur
        LocalDateTime timestamp
) {
}
