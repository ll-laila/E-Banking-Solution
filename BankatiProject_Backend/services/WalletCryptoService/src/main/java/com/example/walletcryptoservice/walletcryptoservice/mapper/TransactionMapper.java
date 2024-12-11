package com.example.walletcryptoservice.walletcryptoservice.mapper;


import com.example.walletcryptoservice.walletcryptoservice.entity.Transaction;
import com.example.walletcryptoservice.walletcryptoservice.response.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionResponse fromTransaction(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getUserBuyId(),
                transaction.getCryptoName(),
                transaction.getAmount(),
                transaction.getPrice(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );


    }


}
