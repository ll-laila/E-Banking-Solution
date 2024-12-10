package com.example.walletcryptoservice.walletcryptoservice.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class CryptoWallet {
    @Id
    private String id;
    private String userId;
    private Map<String, Double> cryptos = new HashMap<>(); // {cryptoName: amount}
    private Map<String, Double> cryptosToSell = new HashMap<>(); // {cryptoName: amount}
}
