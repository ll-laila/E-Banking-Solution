package org.example.batchservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.context.annotation.Configuration;
@Configuration
public class BatchConfig {

    @Autowired
    private TransactionService transactionService;

    @Bean
    public ItemReader<Transaction> reader() {
        return new ItemReader<Transaction>() {
            private Iterator<Transaction> transactionIterator;

            @Override
            public Transaction read() throws Exception {
                if (transactionIterator == null) {
                    List<Transaction> transactions = transactionService.getUnreconciledTransactions();
                    transactionIterator = transactions.iterator();
                }
                return transactionIterator.hasNext() ? transactionIterator.next() : null;
            }
        };
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> processor() {
        return transaction -> {
            boolean isReconciled = transactionService.validateWithProvider(transaction);
            transaction.setStatus(isReconciled ? TransactionStatus.RECONCILED : TransactionStatus.FAILED);
            return transaction;
        };
    }

    @Bean
    public ItemWriter<Transaction> writer() {
        return transactions -> transactions.forEach(transaction -> {
            transactionService.updateTransactionAfterReconciliation(transaction,
                    transaction.getStatus() == TransactionStatus.RECONCILED);
        });
    }

    @Bean
    public Step reconciliationStep(StepBuilderFactory stepBuilderFactory,
                                   ItemReader<Transaction> reader,
                                   ItemProcessor<Transaction, Transaction> processor,
                                   ItemWriter<Transaction> writer) {
        return stepBuilderFactory.get("reconciliationStep")
                .<Transaction, Transaction>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job reconciliationJob(JobBuilderFactory jobBuilderFactory, Step reconciliationStep) {
        return jobBuilderFactory.get("reconciliationJob")
                .incrementer(new RunIdIncrementer())
                .flow(reconciliationStep)
                .end()
                .build();
    }
}


}
