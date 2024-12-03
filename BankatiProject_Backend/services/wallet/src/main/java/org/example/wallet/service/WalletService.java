package org.example.wallet.service;
import org.example.wallet.dto.WalletRequest;
import org.example.wallet.dto.WalletResponse;
import org.example.wallet.entity.Wallet;
import org.example.wallet.mapper.WalletMapper;
import org.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;


@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletMapper walletMapper;

    public Double getWalletBalance(Long clientId) {
        Wallet wallet = walletRepository.findByClientId(clientId);
        if (wallet != null) {
            return wallet.getBalance();
        }
        else {
            throw new RuntimeException("wallet not Found");
        }
    }

    public Long saveWallet(WalletRequest walletRequest) {
        var wallet = walletRepository.save(walletMapper.toWallet(walletRequest));
        return wallet.getId();
    }

    public WalletResponse findWallet(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Wallet with ID %d not found", id)));
        return walletMapper.fromWallet(wallet);
    }





}
