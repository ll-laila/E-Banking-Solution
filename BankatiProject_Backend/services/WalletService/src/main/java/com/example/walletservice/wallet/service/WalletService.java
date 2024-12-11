package com.example.walletservice.wallet.service;

import com.example.walletservice.wallet.entity.BankAccount;
import com.example.walletservice.wallet.entity.Wallet;
import com.example.walletservice.wallet.mapper.BankAccountMapper;
import com.example.walletservice.wallet.mapper.WalletMapper;
import com.example.walletservice.wallet.repository.BankAccountRepository;
import com.example.walletservice.wallet.repository.WalletRepository;
import com.example.walletservice.wallet.request.BankAccountRequest;
import com.example.walletservice.wallet.request.WalletRequest;
import com.example.walletservice.wallet.response.BankAccountResponse;
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
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private BankAccountMapper bankAccountMapper;


    public String saveWallet(WalletRequest walletRequest) {
        BankAccount bankAccount = new BankAccount(null,1000.00);
        var bankAccountSaved = bankAccountRepository.save(bankAccount);

        Wallet wallet = new Wallet(null,walletRequest.balance(), walletRequest.clientId(),bankAccountSaved.getId());
        var walletSaved = walletRepository.save(wallet);
        return walletSaved.getId();
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

    public BankAccountResponse findBankAccountById(String bankAccountId) {
        return bankAccountMapper.fromBankAccount(bankAccountRepository.findById(bankAccountId));

    }


    public WalletResponse updateWallet(WalletRequest walletRequest) {
        Wallet existingWallet = walletRepository.findById(walletRequest.id())
                .orElseThrow(() -> new RuntimeException(format("Wallet with ID %s not found", walletRequest.id())));

        existingWallet.setBalance(walletRequest.balance());
        existingWallet.setBankAccountId(walletRequest.bankAccountId());

        Wallet updatedWallet = walletRepository.save(existingWallet);
        return walletMapper.fromWallet(updatedWallet);
    }




    public String updateBankAccount(BankAccountRequest request) {
        BankAccount existingBankAccount = bankAccountRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException(format("Bk with ID %s not found", request.id())));

        existingBankAccount.setSolde(request.solde());

        BankAccount bankAccount = bankAccountRepository.save(existingBankAccount);
        return bankAccount.getId();
    }




}
