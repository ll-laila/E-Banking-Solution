package com.example.user.transactionClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "transaction-service",
        url = "${application.config.transactions-url}"
)
public interface TransactionClient {

    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request);
}
