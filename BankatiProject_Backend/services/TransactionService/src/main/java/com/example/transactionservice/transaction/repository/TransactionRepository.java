package com.example.transactionservice.transaction.repository;

import com.example.transactionservice.transaction.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

}
