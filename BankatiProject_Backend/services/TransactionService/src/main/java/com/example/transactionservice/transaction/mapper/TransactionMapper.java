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
                .transactionMethod(request.transactionMethod()) // Méthode de transaction
                .beneficiaryId(request.beneficiaryId())
                .beneficiaryRole(request.beneficiaryRole())// Bénéficiaire
                .beneficiaryPhone(request.beneficiaryPhone())
                .transactionType(request.transactionType()) // Type de transaction
                .status(request.status()) // Statut de la transaction
                .currency(request.currency()) // Devise
                .userId(request.userId())
                .validatedDate(request.validatedDate()) // Date de validation
                .userPhone(request.userPhone())
                .userRole(request.userRole())
                .build();
    }


}
