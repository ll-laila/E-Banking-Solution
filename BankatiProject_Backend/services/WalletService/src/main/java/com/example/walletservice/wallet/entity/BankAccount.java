package com.example.walletservice.wallet.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class BankAccount {
    @Id
    private String id;
    private Double solde;
}
