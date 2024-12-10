package com.example.walletcryptoservice.walletcryptoservice.repository;

import com.example.walletcryptoservice.walletcryptoservice.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
