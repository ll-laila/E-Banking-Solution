package com.example.walletservice.wallet.mapper;


import com.example.walletservice.wallet.entity.BankAccount;
import com.example.walletservice.wallet.request.BankAccountRequest;
import com.example.walletservice.wallet.response.BankAccountResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountMapper {

    public BankAccount toBankAccount(BankAccountRequest request) {
        if (request == null) {
            return null;
        }

        return BankAccount.builder()
                .id(request.id())
                .solde(request.solde())
                .build();
    }

    public BankAccountResponse fromBankAccount(Optional<BankAccount> bankAccount) {

        return new BankAccountResponse(
                bankAccount.get().getId(),
                bankAccount.get().getSolde()
        );
    }

}
