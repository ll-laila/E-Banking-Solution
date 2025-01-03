package com.example.transactionservice.transaction.repository;

import com.example.transactionservice.transaction.entity.Transaction;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findBySenderId(String senderId);
    List<Transaction> findBySenderIdAndCreatedDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByBeneficiaryId(String beneficiaryId);
}
