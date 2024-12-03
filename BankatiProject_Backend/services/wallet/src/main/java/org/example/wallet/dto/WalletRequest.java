package org.example.wallet.dto;

public record WalletRequest (
        Long id,
        Double balance,
        Long clientId
) {
}
