package org.example.batchservice.TransactionService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transaction-service", url = "http://localhost:8040/api/v1/transactions")
public interface TransactionFeignClient {
//
//    @GetMapping("/batch")
//    List<Transaction> getTransactionsForBatch();
}
