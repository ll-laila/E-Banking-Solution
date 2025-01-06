package com.example.virtualcard.virtualcard.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class VirtualCard {
    @Id
    private String id;

    private String cardNumber;

    private String userId;

    private LocalDateTime expirationDate;

    private String status; // Status : "ACTIVE", "INACTIVE", "EXPIRED"

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private double montant;


}
