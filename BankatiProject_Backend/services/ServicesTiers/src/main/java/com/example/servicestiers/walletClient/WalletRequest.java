package com.example.servicestiers.walletClient;

public record WalletRequest(
        Long id,
        Double balance,
        String clientId
) {
}
