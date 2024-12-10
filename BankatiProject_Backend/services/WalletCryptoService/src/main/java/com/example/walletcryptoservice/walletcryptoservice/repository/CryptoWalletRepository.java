package com.example.walletcryptoservice.walletcryptoservice.repository;
import com.example.walletcryptoservice.walletcryptoservice.entity.CryptoWallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CryptoWalletRepository  extends MongoRepository<CryptoWallet, String> {

    CryptoWallet findByUserId(String userId);


}

