package com.example.transactionservice.transaction.service;


import com.example.transactionservice.kafka.TransactionConfirmation;
import com.example.transactionservice.kafka.TransactionProducer;
import com.example.transactionservice.transaction.entity.Transaction;
import com.example.transactionservice.transaction.entity.TransactionMethod;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import com.example.transactionservice.transaction.entity.TransactionType;
import com.example.transactionservice.transaction.mapper.TransactionMapper;
import com.example.transactionservice.transaction.repository.TransactionRepository;
import com.example.transactionservice.transaction.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final TransactionProducer transactionProducer;

    public String createTransaction(TransactionRequest request) {
        Transaction transaction = mapper.toTransaction(request);
        transaction = repository.save(transaction);
        System.out.println("Start sendTransactionNotification for {}"+ request);
        sendTransactionNotification(request);
        System.out.println("Start sendTransactionNotification for {}"+ request);
        return transaction.getId();
    }

    private void sendTransactionNotification(TransactionRequest request) {
        try {
            TransactionConfirmation transactionConfirmation = new TransactionConfirmation(
                    request.id(), // ID de la transaction
                    request.amount(), // Montant de la transaction
                    request.transactionType(), // Type de transaction
                    request.status(), // Statut de la transaction
                    request.senderCurrency(),
                    request.beneficiaryCurrency(),// Devise
                    request.beneficiaryName(),
                    request.beneficiaryPhone(),
                    request.senderName(),
                    request.senderPhoneNumber(),
                    request.validatedDate() // Date de validation
            );

            transactionProducer.sendOrderConfirmation(transactionConfirmation);
            System.out.println("Notification envoyée avec succès pour la transaction ID = " + request.id());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la notification pour la transaction ID = " + request.id() + " : " + e.getMessage());
        }
    }


    public Transaction getTransactionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction introuvable pour l'ID : " + id));
    }
    public List<Transaction> getAllTransactionsByUser(String userId) {
        return repository.findBySenderId(userId);
    }

    public Transaction updateTransactionStatus(String transactionId, TransactionStatus status) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction introuvable pour l'ID : " + transactionId));
        transaction.setStatus(status);
        return repository.save(transaction);
    }
    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return repository.findByStatus(status);
    }
    public Transaction cancelTransaction(String transactionId) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction introuvable pour l'ID : " + transactionId));
        transaction.setStatus(TransactionStatus.CANCELLED);
        return repository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findBySenderIdAndCreatedDateBetween(userId, startDate, endDate);
    }

    public Transaction retryTransaction(String transactionId) {
        Transaction transaction = repository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction introuvable pour l'ID : " + transactionId));

        if (!transaction.getStatus().equals(TransactionStatus.FAILED)) {
            throw new IllegalStateException("Seules les transactions échouées peuvent être réessayées.");
        }
        // Simuler un nouvel essai de la transaction
        transaction.setStatus(TransactionStatus.PENDING);
        transaction = repository.save(transaction);

        // Simuler la confirmation (remplacez ceci par une intégration réelle)
        transaction.setStatus(TransactionStatus.COMPLETED);
        return repository.save(transaction);
    }


}
