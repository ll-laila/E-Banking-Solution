package com.example.virtualcard.walletClient;

public record Wallet(
        String id,
        Double balance,
        String clientId,
        String bankAccountId
) {
}
