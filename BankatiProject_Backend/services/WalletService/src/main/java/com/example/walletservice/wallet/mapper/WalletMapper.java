package com.example.walletservice.wallet.mapper;

import com.example.walletservice.wallet.entity.BankAccount;
import com.example.walletservice.wallet.entity.Wallet;
import com.example.walletservice.wallet.request.BankAccountRequest;
import com.example.walletservice.wallet.request.WalletRequest;
import com.example.walletservice.wallet.response.BankAccountResponse;
import com.example.walletservice.wallet.response.WalletResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletMapper {

    public Wallet toWallet(WalletRequest request) {
        if (request == null) {
            return null;
        }

        BankAccount bankAccount = new BankAccount(request.bankAccountRequest().id(),request.bankAccountRequest().solde());

        return Wallet.builder()
                .id(request.id())
                .balance(request.balance())
                .clientId(request.clientId())
                .bankAccount(bankAccount)
                .build();
    }

    public WalletResponse fromWallet(Wallet wallet) {

        BankAccountResponse bankAccountResponse = new BankAccountResponse(wallet.getBankAccount().getId(),wallet.getBankAccount().getSolde());
        return new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getClientId(),
                bankAccountResponse
        );
    }


}
