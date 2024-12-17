package com.example.transactionservice.transaction.service;

import com.example.transactionservice.transaction.entity.Subscription;
import com.example.transactionservice.transaction.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;

    public Subscription createSubscription(String userId, String agentId ,BigDecimal price, int durationInMonths) {
        Subscription subscription = Subscription.builder()
                .userId(userId)
                .agentId(agentId)
                .price(price)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(durationInMonths))
                .active(true)
                .build();
        return repository.save(subscription);
    }

    public List<Subscription> getUserSubscriptions(String userId) {
        return repository.findByUserId(userId);
    }

    public Subscription cancelSubscription(String subscriptionId) {
        Subscription subscription = repository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Abonnement introuvable pour l'ID : " + subscriptionId));
        subscription.setActive(false);
        return repository.save(subscription);
    }

    public void deactivateExpiredSubscriptions() {
        List<Subscription> activeSubscriptions = repository.findByActive(true);
        for (Subscription subscription : activeSubscriptions) {
            if (subscription.getEndDate().isBefore(LocalDateTime.now())) {
                subscription.setActive(false);
                repository.save(subscription);
            }
        }
    }
}
