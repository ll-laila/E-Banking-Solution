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

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String beneficiaryId; // Bénéficiaire (si applicable)
    @CreatedDate
    @Column(updatable = false, nullable = false)
     private String beneficiaryName;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String beneficiaryPhone;
    @CreatedDate
    @Column(updatable = false, nullable = false)
     private Role beneficiaryRole;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // Paiement, Transfert, etc.

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, COMPLETED, FAILED

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String senderCurrency; // Devise (EUR, USD, MAD)

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String beneficiaryCurrency;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String senderId;
    @CreatedDate
    @Column(updatable = false, nullable = false)
     private String senderName;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private String senderPhoneNumber;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Role senderRole;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    private LocalDateTime validatedDate; // Date de validatio



}