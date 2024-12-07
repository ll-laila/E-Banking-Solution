package com.example.servicestiers.walletClient;

public record WalletResponse(
        Long id,
        Double balance,
        String clientId
) {
}
