package com.example.servicestiers.CMI.service;

import com.example.servicestiers.CMI.exception.InsufficientFundsException;
import com.example.servicestiers.CMI.request.TransferRequest;
import com.example.servicestiers.CMI.request.WalletTransactionRequest;
import com.example.servicestiers.CMI.response.TransferResponse;
import com.example.servicestiers.walletClient.WalletClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CMIService {

    @Autowired
    private WalletClient walletClient;

    public boolean checkBalance(Long userId, Double amount) {
        Double currentBalance = walletClient.getWallet(userId).getBody().balance();
        return currentBalance >= amount;
    }

    public TransferResponse transferAmount(TransferRequest request) {
        boolean hasEnoughBalance = checkBalance(request.senderId(), request.amount());
        if (!hasEnoughBalance) {
            throw new InsufficientFundsException("Not enough balance to perform this transaction");
        }

        walletClient.debitWallet(new WalletTransactionRequest(request.senderId(), request.amount()));
        walletClient.creditWallet(new WalletTransactionRequest(request.receiverId(), request.amount()));

        return new TransferResponse("Transfer successful", request.amount());
    }
}
