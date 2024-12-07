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
                .transactionMethod(request.transactionMethod()) // Méthode de transaction
                .amount(request.amount()) // Montant
                .beneficiaryId(request.beneficiaryId()) // Bénéficiaire
                .transactionType(request.transactionType()) // Type de transaction
                .status(request.status()) // Statut de la transaction
                .currency(request.currency()) // Devise
                .externalReference(request.externalReference()) // Référence externe
                .validatedDate(request.validatedDate()) // Date de validation
                .build();
    }


}
