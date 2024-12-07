package com.example.user.walletClient;

public record WalletRequest(
        Long id,
        Double balance,
        String clientId
) {
}
