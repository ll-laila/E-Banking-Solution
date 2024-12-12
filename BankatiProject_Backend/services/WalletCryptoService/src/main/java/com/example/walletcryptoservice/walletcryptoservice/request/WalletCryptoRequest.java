package com.example.walletcryptoservice.walletcryptoservice.request;

import lombok.Builder;

import java.util.Map;

@Builder
public record WalletCryptoRequest(
        String id,
        String userId,
        Map<String, Double> cryptos

) {}
