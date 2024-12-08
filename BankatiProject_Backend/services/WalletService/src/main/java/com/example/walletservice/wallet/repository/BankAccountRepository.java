package com.example.walletservice.wallet.repository;

import com.example.walletservice.wallet.entity.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

}

