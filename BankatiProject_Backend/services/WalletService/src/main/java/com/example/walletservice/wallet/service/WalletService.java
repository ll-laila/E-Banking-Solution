package com.example.walletservice.wallet.service;

import com.example.walletservice.wallet.entity.Wallet;
import com.example.walletservice.wallet.mapper.WalletMapper;
import com.example.walletservice.wallet.repository.WalletRepository;
import com.example.walletservice.wallet.request.WalletRequest;
import com.example.walletservice.wallet.response.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletMapper walletMapper;

    public String saveWallet(WalletRequest walletRequest) {
        var wallet = walletRepository.save(walletMapper.toWallet(walletRequest));
        return wallet.getId();
    }

    public WalletResponse findWallet(String id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Wallet with ID %d not found", id)));
        return walletMapper.fromWallet(wallet);
    }


    public WalletResponse findWalletByIdClient(String clientId) {
        Wallet wallet = walletRepository.findWalletByClientId(clientId);
        return walletMapper.fromWallet(wallet);
    }


}
