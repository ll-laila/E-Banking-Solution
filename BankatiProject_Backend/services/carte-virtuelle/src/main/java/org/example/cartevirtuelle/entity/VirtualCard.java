package org.example.cartevirtuelle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "virtual_cards")
public class VirtualCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private Long userId; // Id de l'utilisateur auquel la carte appartient

    private LocalDateTime expirationDate;

    private String status; // Status : "ACTIVE", "INACTIVE", "EXPIRED"

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

