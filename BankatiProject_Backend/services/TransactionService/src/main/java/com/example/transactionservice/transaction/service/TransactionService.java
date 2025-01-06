package com.example.transactionservice.transaction.service;


import com.example.transactionservice.kafka.TransactionConfirmation;
import com.example.transactionservice.kafka.TransactionProducer;
import com.example.transactionservice.servicesTiersClient.ServicesTiersClient;
import com.example.transactionservice.servicesTiersClient.TiersClientRequest;
import com.example.transactionservice.servicesTiersClient.TiersClientResponse;
import com.example.transactionservice.transaction.entity.*;
import com.example.transactionservice.transaction.mapper.TransactionMapper;
import com.example.transactionservice.transaction.repository.TransactionRepository;
import com.example.transactionservice.transaction.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final ServicesTiersClient servicesTiersClient;

    public String createTransaction(TransactionRequest request) {

        TiersClientRequest tiersClientRequest = new TiersClientRequest(
                request.senderCurrency(),
                request.beneficiaryCurrency(),
                request.amount(),
                request.senderId(),
                request.beneficiaryId()
        );
        // Appeler le service tiers
        ResponseEntity<TiersClientResponse> responseEntity = servicesTiersClient.doTransaction(tiersClientRequest);

        // Vérifier si le service tiers a répondu correctement
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            TiersClientResponse response = responseEntity.getBody();
            if (response.isValid() != null && !response.isValid()) {
                throw new IllegalStateException("Transaction invalide selon le service tiers.");
            }
        } else {
            throw new IllegalStateException("Échec de l'appel au service tiers.");
        }

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

    public List<Transaction> getAllTransactionsByUserId(String userId) {
        // Recherche les transactions où l'utilisateur est le sender
        List<Transaction> senderTransactions = repository.findBySenderId(userId);
        // Recherche les transactions où l'utilisateur est le beneficiary
        List<Transaction> beneficiaryTransactions = repository.findByBeneficiaryId(userId);

        // Combinaison des deux listes de transactions
        senderTransactions.addAll(beneficiaryTransactions);

        return senderTransactions;
    }


    //For Batch Service

    //les transactions qui nécessitent une conciliation.
    public List<Transaction> getUnreconciledTransactions() {
        return repository.findByStatus(TransactionStatus.PENDING);
    }

//    public void updateTransactionAfterReconciliation(Transaction transaction, boolean isReconciled) {
//        transaction.setStatus(isReconciled ? TransactionStatus.RECONCILED : TransactionStatus.FAILED);
//        repository.save(transaction);
//    }

    public boolean validateWithProvider(Transaction transaction) {
        TiersClientRequest tiersClientRequest = new TiersClientRequest(
                transaction.getSenderCurrency(),
                transaction.getBeneficiaryCurrency(),
                transaction.getAmount(),
                transaction.getSenderId(),
                transaction.getBeneficiaryId()
        );

        ResponseEntity<TiersClientResponse> responseEntity = servicesTiersClient.doTransaction(tiersClientRequest);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody().isValid();
        }

        return false;
    }


}
