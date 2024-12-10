package com.example.walletcryptoservice.walletcryptoservice.service;

import com.example.walletcryptoservice.walletClient.WalletClient;
import com.example.walletcryptoservice.walletClient.Wallet;
import com.example.walletcryptoservice.walletcryptoservice.entity.CryptoWallet;
import com.example.walletcryptoservice.walletcryptoservice.entity.Transaction;
import com.example.walletcryptoservice.walletcryptoservice.mapper.TransactionMapper;
import com.example.walletcryptoservice.walletcryptoservice.repository.CryptoWalletRepository;
import com.example.walletcryptoservice.walletcryptoservice.repository.TransactionRepository;
import com.example.walletcryptoservice.walletcryptoservice.response.TransactionResponse;
import com.example.walletcryptoservice.walletcryptoservice.response.WalletCryptoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void buyCrypto(String userBuyId, String userSenderId, String cryptoName, double amount) {
        double price = cryptoService.getCryptoPrice(cryptoName);
        CryptoWallet wallet = walletRepository.findByUserId(userBuyId);
        CryptoWallet walletSender = walletRepository.findByUserId(userSenderId);

        if (wallet == null) {
            wallet = new CryptoWallet();
            wallet.setUserId(userBuyId);
        }
        if (walletSender == null) {
            walletSender = new CryptoWallet();
            walletSender.setUserId(userSenderId);
        }

        double totaClost = price * amount;

        wallet.getCryptos().put(cryptoName, wallet.getCryptos().get(cryptoName) + amount);
        walletRepository.save(wallet);

        walletSender.getCryptosToSell().put(cryptoName, walletSender.getCryptosToSell().get(cryptoName) - amount);
        walletRepository.save(walletSender);

        Transaction transaction = new Transaction();
        transaction.setUserBuyId(userBuyId);
        transaction.setUserSendId(userSenderId);
        transaction.setCryptoName(cryptoName);
        transaction.setAmount(amount);
        transaction.setPrice(totaClost);
        transaction.setTransactionType("BUY");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }





    public void setCryptosToSell(String userId, Map<String, Double> cryptosToSell) {
        CryptoWallet wallet = walletRepository.findByUserId(userId);

        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user: " + userId);
        }

        // Validation : Vérifiez que l'utilisateur ne vend pas plus qu'il ne possède
        for (Map.Entry<String, Double> entry : cryptosToSell.entrySet()) {
            String cryptoName = entry.getKey();
            double amountToSell = entry.getValue();

            double availableAmount = wallet.getCryptos().getOrDefault(cryptoName, 0.0);
            if (amountToSell > availableAmount) {
                throw new IllegalArgumentException(
                        String.format("Cannot sell %f %s. Available: %f.", amountToSell, cryptoName, availableAmount)
                );
            }
        }

        wallet.setCryptosToSell(cryptosToSell);
        walletRepository.save(wallet);
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
        double valueInClassicalMoney = amount * cryptoPrice;


        Wallet classicalWallet = walletClient.getWalletByIdClient(userId).getBody();
        if (classicalWallet == null) {
            throw new IllegalArgumentException("Classical wallet not found for user: " + userId);
        }


        Wallet updatedWallet = new Wallet(
                classicalWallet.id(),
                classicalWallet.balance() + valueInClassicalMoney,
                classicalWallet.clientId(),
                classicalWallet.bankAccount()
        );


        walletClient.saveWallet(updatedWallet);


        cryptoWallet.getCryptos().put(cryptoName, availableCrypto - amount);
        walletRepository.save(cryptoWallet);


        // Enregistrer la transaction
        Transaction transaction = new Transaction();
        transaction.setUserBuyId(userId);
        transaction.setCryptoName(cryptoName);
        transaction.setAmount(amount);
        transaction.setPrice(cryptoPrice);
        transaction.setTransactionType("TRANSFER DU CRYPTO À L'ARGENT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

    }



    public Map<String, Map<String, Double>> getCryptosToSell() {
        List<CryptoWallet> wallets = walletRepository.findAll();
        Map<String, Map<String, Double>> cryptosToSell = new HashMap<>();

        for (CryptoWallet wallet : wallets) {
            if (!wallet.getCryptosToSell().isEmpty()) {
                cryptosToSell.put(wallet.getUserId(), new HashMap<>(wallet.getCryptosToSell()));
            }
        }
        return cryptosToSell;
    }



    public List<TransactionResponse> findAllUserTransactions(String userId) {
        return transactionRepository.findAll()
                .stream()
                .filter(transaction -> userId.equals(transaction.getUserBuyId()) || userId.equals(transaction.getUserSendId()))
                .map(transactionMapper::fromTransaction)
                .collect(Collectors.toList());
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
                .cryptosToSell(wallet.getCryptosToSell())
                .build();
    }


}




