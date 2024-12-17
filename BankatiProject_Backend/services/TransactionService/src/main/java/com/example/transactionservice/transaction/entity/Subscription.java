package com.example.transactionservice.transaction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userId; // L'utilisateur lié à l'abonnement
    private String agentId;

    private BigDecimal price; // Prix de l'abonnement

    private LocalDateTime startDate; // Date de début de l'abonnement

    private LocalDateTime endDate; // Date de fin de l'abonnement

    private boolean active; // Indique si l'abonnement est actif
}

