package com.example.walletcryptoservice.walletClient;

public record Wallet(
        String id,
        Double balance,
        String clientId,
        BankAccount bankAccount
) {
}
