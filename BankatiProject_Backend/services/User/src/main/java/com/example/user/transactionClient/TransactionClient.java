package com.example.user.transactionClient;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "transaction-service",
        url = "${application.config.transactions-url}"
)
public interface TransactionClient {



}
