package com.example.user.walletClient;

public record WalletResponse(
        String id,
        Double balance,
        String clientId,
        String bankAccountId


) {
}
