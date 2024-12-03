package org.example.user.wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private Double balance;
    private Long clientId;

}