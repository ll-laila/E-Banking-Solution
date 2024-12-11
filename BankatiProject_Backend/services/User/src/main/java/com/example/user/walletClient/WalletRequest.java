package com.example.user.walletClient;

public record WalletRequest(
        String id,
        Double balance,
        String clientId,
        String bankAccountId


) {
}
