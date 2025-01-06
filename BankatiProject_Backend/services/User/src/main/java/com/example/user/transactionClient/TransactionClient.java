package com.example.user.transactionClient;


import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;



@FeignClient(
        name = "transaction-service",
        url = "${application.config.transactions-url}"
)
public interface TransactionClient {

    @PostMapping("/createTransaction")
    ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request);
    @PostMapping("/createSubscription")
    ResponseEntity<String> createSubscription(@RequestBody SubscriptionRequest request);
    @GetMapping("/user/{userId}")
    List<TransactionResponse> getTransactionsByUser(@PathVariable("userId") String userId);

    @GetMapping("/user-transactions/{userId}")
    List<TransactionResponse> getAllTransactionsByUserId(@PathVariable("userId") String userId);

    @GetMapping("/subscriptions/{userId}")
     ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable("userId") String userId);


}
