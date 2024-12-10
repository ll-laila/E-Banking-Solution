package com.example.walletcryptoservice.walletcryptoservice.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record WalletCryptoResponse(
        String id,
        String userId,
        Map<String, Double> cryptos,
        Map<String, Double> cryptosToSell
) {}
