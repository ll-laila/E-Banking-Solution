package com.example.walletservice.wallet.response;

import com.example.walletservice.wallet.request.BankAccountRequest;

public record WalletResponse(
        String id,
        Double balance,
        String clientId,

        BankAccountRequest bankAccountRequest

){
}
