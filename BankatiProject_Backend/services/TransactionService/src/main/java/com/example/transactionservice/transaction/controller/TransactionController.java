package com.example.transactionservice.transaction.controller;

import com.example.transactionservice.transaction.entity.Subscription;
import com.example.transactionservice.transaction.entity.Transaction;
import com.example.transactionservice.transaction.entity.TransactionStatus;
import com.example.transactionservice.transaction.request.SubscriptionRequest;
import com.example.transactionservice.transaction.request.TransactionRequest;
import com.example.transactionservice.transaction.service.SubscriptionService;
import com.example.transactionservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final SubscriptionService subscriptionService;

    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request) {
        String transactionId = transactionService.createTransaction(request);
        return ResponseEntity.ok(transactionId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    // Récupérer toutes les transactions d’un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUser(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByUser(userId);
        return ResponseEntity.ok(transactions);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable String id,
            @RequestParam TransactionStatus status) {
        Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok(updatedTransaction);
    }


    @GetMapping("/status")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(@RequestParam TransactionStatus status) {
        List<Transaction> transactions = transactionService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    // Annuler une transaction
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Transaction> cancelTransaction(@PathVariable String id) {
        Transaction cancelledTransaction = transactionService.cancelTransaction(id);
        return ResponseEntity.ok(cancelledTransaction);
    }



    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @RequestParam String userId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionHistory(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    // Réessayer une transaction échouée
    @PostMapping("/{id}/retry")
    public ResponseEntity<Transaction> retryTransaction(@PathVariable String id) {
        Transaction retriedTransaction = transactionService.retryTransaction(id);
        return ResponseEntity.ok(retriedTransaction);
    }


    @PostMapping("/createSubscription")
    public ResponseEntity<String> createSubscription(@RequestBody SubscriptionRequest request) {
        Subscription subscription = subscriptionService.createSubscription(
                request.userId(),request.agentId(), request.price(), request.durationInMonths());
        return ResponseEntity.ok("Subscription créée avec succès !");
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable String userId) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("/cancel/{subscriptionId}")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable String subscriptionId) {
        Subscription subscription = subscriptionService.cancelSubscription(subscriptionId);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/deactivateExpired")
    public ResponseEntity<Void> deactivateExpiredSubscriptions() {
        subscriptionService.deactivateExpiredSubscriptions();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-transactions/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUserId(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
}

