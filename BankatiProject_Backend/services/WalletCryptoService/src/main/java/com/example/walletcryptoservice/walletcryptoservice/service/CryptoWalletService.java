package com.example.walletcryptoservice.walletcryptoservice.service;

import com.example.walletcryptoservice.walletClient.WalletClient;
import com.example.walletcryptoservice.walletClient.Wallet;
import com.example.walletcryptoservice.walletcryptoservice.entity.CryptoWallet;
import com.example.walletcryptoservice.walletcryptoservice.entity.Transaction;
import com.example.walletcryptoservice.walletcryptoservice.mapper.TransactionMapper;
import com.example.walletcryptoservice.walletcryptoservice.repository.CryptoWalletRepository;
import com.example.walletcryptoservice.walletcryptoservice.repository.TransactionRepository;
import com.example.walletcryptoservice.walletcryptoservice.request.WalletCryptoRequest;
import com.example.walletcryptoservice.walletcryptoservice.response.TransactionResponse;
import com.example.walletcryptoservice.walletcryptoservice.response.WalletCryptoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class CryptoWalletService {

    @Autowired
    private CryptoWalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private WalletClient walletClient;

    @Autowired
    private TransactionMapper transactionMapper;


    public String saveWalletCrypto(WalletCryptoRequest walletRequest) {
        CryptoWallet cryptoWallet = new CryptoWallet(null,walletRequest.userId(),walletRequest.cryptos());
        var walletSaved = walletRepository.save(cryptoWallet);
        return walletSaved.getId();
    }

    @Transactional
    public String buyCrypto(String userId, String cryptoName, double quantity) {
        double price = cryptoService.getCryptoPrice(cryptoName);
        CryptoWallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            wallet = new CryptoWallet();
            wallet.setUserId(userId);
        }

        //from USD to DH
        double totaClost = price * quantity * 10.00;


        wallet.getCryptos().put(cryptoName, wallet.getCryptos().getOrDefault(cryptoName,0.0) + quantity);

        Wallet walletToUpdated = walletClient.getWalletByIdClient(userId).getBody();
        Wallet walletUpdate = new Wallet(walletToUpdated.id(),walletToUpdated.balance()-totaClost,walletToUpdated.clientId(),walletToUpdated.bankAccountId());
        walletClient.updateWallet(walletUpdate);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setUserBuyId(userId);
        transaction.setCryptoName(cryptoName);
        transaction.setAmount(quantity);
        transaction.setPrice(totaClost);
        transaction.setTransactionType("ACHAT");
        transaction.setTimestamp(LocalDateTime.now());
        return  transactionRepository.save(transaction).getId();

    }




    @Transactional
    public String sellCrypto(String userId, String cryptoName, double quantity) {
        double price = cryptoService.getCryptoPrice(cryptoName);
        CryptoWallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            wallet = new CryptoWallet();
            wallet.setUserId(userId);
        }

        //from USD to DH
        double totaClost = price * quantity * 10.00;

        wallet.getCryptos().put(cryptoName, wallet.getCryptos().getOrDefault(cryptoName,0.0) - quantity);


        Wallet walletToUpdated = walletClient.getWalletByIdClient(userId).getBody();
        Wallet walletSenderUpdate = new Wallet(walletToUpdated.id(),walletToUpdated.balance()+totaClost,walletToUpdated.clientId(),walletToUpdated.bankAccountId());
        walletClient.updateWallet(walletSenderUpdate);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setUserBuyId(userId);
        transaction.setCryptoName(cryptoName);
        transaction.setAmount(quantity);
        transaction.setPrice(totaClost);
        transaction.setTransactionType("VENTE");
        transaction.setTimestamp(LocalDateTime.now());
        return  transactionRepository.save(transaction).getId();

    }



    public WalletCryptoResponse getWalletByUserId(String userId) {
        CryptoWallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            throw new RuntimeException("Wallet not found for userId: " + userId);
        }

        return WalletCryptoResponse.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .cryptos(wallet.getCryptos())
                .build();
    }




    public List<TransactionResponse> findAllUserTransactions(String userId) {
        return transactionRepository.findAll()
                .stream()
                .filter(transaction -> userId.equals(transaction.getUserBuyId()))
                .map(transactionMapper::fromTransaction)
                .collect(Collectors.toList());
    }




    public void transferCryptoToClassicalMoney(String userId, String cryptoName, double amount) {

        CryptoWallet cryptoWallet = walletRepository.findByUserId(userId);
        if (cryptoWallet == null) {
            throw new IllegalArgumentException("Crypto wallet not found for user: " + userId);
        }

        double availableCrypto = cryptoWallet.getCryptos().get(cryptoName);
        if (availableCrypto < amount) {
            throw new IllegalArgumentException("Insufficient crypto balance for " + cryptoName);
        }


        double cryptoPrice = cryptoService.getCryptoPrice(cryptoName);
        double valueInClassicalMoney = amount * cryptoPrice * 10.00;   // to DH


        Wallet classicalWallet = walletClient.getWalletByIdClient(userId).getBody();
        if (classicalWallet == null) {
            throw new IllegalArgumentException("Classical wallet not found for user: " + userId);
        }


        Wallet updatedWallet = new Wallet(
                classicalWallet.id(),
                classicalWallet.balance() + valueInClassicalMoney,
                classicalWallet.clientId(),
                classicalWallet.bankAccountId()
        );


        walletClient.updateWallet(updatedWallet);

        cryptoWallet.getCryptos().put(cryptoName, availableCrypto - amount);
        walletRepository.save(cryptoWallet);


        // Enregistrer la transaction
        Transaction transaction = new Transaction();
        transaction.setUserBuyId(userId);
        transaction.setCryptoName(cryptoName);
        transaction.setAmount(amount);
        transaction.setPrice(valueInClassicalMoney);
        transaction.setTransactionType("TRANSFER DU CRYPTO À L'ARGENT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

    }



}




