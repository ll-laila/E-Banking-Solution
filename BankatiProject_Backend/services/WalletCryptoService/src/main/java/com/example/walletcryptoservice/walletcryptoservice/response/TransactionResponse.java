package com.example.walletcryptoservice.walletcryptoservice.response;

import java.time.LocalDateTime;

public record TransactionResponse (
         String id,
         String userBuyId,
         String cryptoName,
         double amount,
         double price,
         String transactionType,
         LocalDateTime timestamp
) {
}
