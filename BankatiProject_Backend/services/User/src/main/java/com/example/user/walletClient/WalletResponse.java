package com.example.user.walletClient;

public record WalletResponse(
        Long id,
        Double balance,
        String clientId
) {
}
