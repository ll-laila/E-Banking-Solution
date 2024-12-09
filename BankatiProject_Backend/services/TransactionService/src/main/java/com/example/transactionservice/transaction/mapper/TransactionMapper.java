package com.example.transactionservice.transaction.mapper;

import com.example.transactionservice.transaction.entity.Transaction;
import com.example.transactionservice.transaction.request.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {
    public Transaction toTransaction(TransactionRequest request) {
        if (request == null) {
            return null;
        }
        return Transaction.builder()
                .id(request.id())
                .amount(request.amount()) // Montant
                .beneficiaryId(request.beneficiaryId()) // Bénéficiaire
                .beneficiaryName(request.beneficiaryName())
                .beneficiaryPhone(request.beneficiaryPhone())
                .beneficiaryRole(request.beneficiaryRole())
                .transactionType(request.transactionType()) // Type de transaction
                .status(request.status()) // Statut de la transaction
                .senderCurrency(request.senderCurrency())
                .beneficiaryCurrency(request.beneficiaryCurrency())// Devise
                .validatedDate(request.validatedDate()) // Date de validation
                .senderId(request.senderId())
                .senderName(request.senderName())
                .senderPhoneNumber(request.senderPhoneNumber())
                .senderRole(request.senderRole())
                .build();
    }


}
