package org.example.wallet.mapper;


import org.example.wallet.dto.WalletRequest;
import org.example.wallet.dto.WalletResponse;
import org.example.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
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
