package com.example.walletservice.wallet.response;


public record WalletResponse(
        String id,
        Double balance,
        String clientId,
      String bankAccountId
      //  BankAccountResponse bankAccountResponse

){
}
