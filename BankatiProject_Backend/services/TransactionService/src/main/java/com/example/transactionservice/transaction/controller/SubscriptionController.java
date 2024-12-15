package com.example.transactionservice.transaction.controller;


import com.example.transactionservice.transaction.entity.Subscription;
import com.example.transactionservice.transaction.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(
            @RequestParam String userId,
            @RequestParam String planName,
            @RequestParam BigDecimal price,
            @RequestParam int durationInMonths) {
        Subscription subscription = subscriptionService.createSubscription(userId, planName, price, durationInMonths);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/{userId}")
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
}

