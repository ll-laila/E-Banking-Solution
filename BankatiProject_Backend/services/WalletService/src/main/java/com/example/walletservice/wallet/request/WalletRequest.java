package com.example.walletservice.wallet.request;

public record WalletRequest(
        String id,
        Double balance,
        String clientId,
       String bankAccountId
        //BankAccountRequest bankAccountRequest

){
}
