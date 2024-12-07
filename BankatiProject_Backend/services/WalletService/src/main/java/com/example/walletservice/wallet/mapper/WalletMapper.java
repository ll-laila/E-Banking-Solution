package com.example.walletservice.wallet.mapper;

import com.example.walletservice.wallet.entity.Wallet;
import com.example.walletservice.wallet.request.WalletRequest;
import com.example.walletservice.wallet.response.WalletResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletMapper {

    public Wallet toWallet(WalletRequest request) {
        if (request == null) {
            return null;
        }
        return Wallet.builder()
                .id(request.id())
                .balance(request.balance())
                .clientId(request.clientId())
                .build();
    }

    public WalletResponse fromWallet(Wallet wallet) {

        return new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getClientId()

        );
    }


}