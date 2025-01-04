package org.example.batchservice.TransactionService;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchProcessingService {

    private final TransactionFeignClient transactionFeignClient;

    public List<Transaction> fetchTransactionsForProcessing() {
        return transactionFeignClient.getTransactionsForBatch();
    }
}
