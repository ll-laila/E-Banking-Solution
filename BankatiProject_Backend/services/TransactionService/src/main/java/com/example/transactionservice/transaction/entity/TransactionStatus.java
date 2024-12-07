package com.example.transactionservice.transaction.entity;

public enum TransactionStatus {
    PENDING,      // En attente de validation
    COMPLETED,    // Transaction complétée avec succès
    FAILED,       // Échec de la transaction
    CANCELLED,    // Transaction annulée
    REFUNDED
}
