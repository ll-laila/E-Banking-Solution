package com.example.user.walletCryptoClient;

import lombok.Builder;

import java.util.Map;

@Builder
public record WalletCryptoRequest(
        String id,
        String userId,
        Map<String, Double> cryptos
) {}
