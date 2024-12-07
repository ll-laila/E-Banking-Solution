package com.example.transactionservice.transaction.entity;

public enum TransactionType {
    PAYMENT,          // Paiement d'une facture ou d'un service
    TRANSFER,         // Transfert d'argent entre utilisateurs
    TOP_UP,           // Recharge du portefeuille
    PURCHASE,         // Achat chez un marchand
    SUBSCRIPTION,     // Paiement récurrent pour des abonnements
    WITHDRAWAL
}
