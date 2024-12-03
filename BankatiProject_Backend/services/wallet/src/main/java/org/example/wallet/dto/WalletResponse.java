package org.example.wallet.dto;

public record WalletResponse (
         Long id,
         Double balance,
         Long clientId
) {
}
