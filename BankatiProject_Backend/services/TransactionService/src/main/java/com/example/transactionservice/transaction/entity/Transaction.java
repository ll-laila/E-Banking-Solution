package com.example.transactionservice.transaction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    private Integer beneficiaryId; // Bénéficiaire (si applicable)

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // Paiement, Transfert, etc.

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, COMPLETED, FAILED

    private String currency; // Devise (EUR, USD, MAD)

    private String externalReference; // Référence système tiers

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    private String userId;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    private LocalDateTime validatedDate; // Date de validatio



}