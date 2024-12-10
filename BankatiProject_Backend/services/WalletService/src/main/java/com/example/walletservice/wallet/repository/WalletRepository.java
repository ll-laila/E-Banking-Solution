package com.example.walletservice.wallet.repository;
import com.example.walletservice.wallet.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Wallet findWalletByClientId(String clientId);
}

