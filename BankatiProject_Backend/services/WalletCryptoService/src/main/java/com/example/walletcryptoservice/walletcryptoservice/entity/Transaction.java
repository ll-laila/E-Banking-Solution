package com.example.walletcryptoservice.walletcryptoservice.entity;
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
public class Transaction {
    @Id
    private String id;
    private String userBuyId;
    private String cryptoName;
    private double amount;
    private double price;
    private String transactionType; // BUY or SELL
    private LocalDateTime timestamp;

}
