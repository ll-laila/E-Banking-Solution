package com.example.transactionservice.transaction.repository;


import com.example.transactionservice.transaction.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByUserId(String userId);

    List<Subscription> findByActive(boolean active);
}