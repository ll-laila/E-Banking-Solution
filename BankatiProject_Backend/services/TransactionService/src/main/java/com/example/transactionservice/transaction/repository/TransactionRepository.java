package com.example.transactionservice.transaction.repository;

import com.example.transactionservice.transaction.entity.Transaction;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByStatus(TransactionStatus status);
}
