package com.example.walletcryptoservice.walletClient;

public record Wallet(
        String id,
        Double balance,
        String clientId,
        String bankAccountId
) {
}
