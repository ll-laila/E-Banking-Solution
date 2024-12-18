package com.example.user.users.service;


import com.example.user.transactionClient.TransactionRequest;
import com.example.user.transactionClient.TransactionStatus;
import com.example.user.transactionClient.TransactionType;
import com.example.user.serviceTiersClient.ServiceTiersClient;
import com.example.user.transactionClient.TransactionClient;
import com.example.user.transactionClient.TransactionResponse;
import com.example.user.users.entity.Client;
import com.example.user.users.repository.ClientRepository;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletResponse;
import com.example.user.users.response.AgentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {


    //-------------------------laila-------------------------//
    // laila here




    //-------------------------chaima-------------------------//
    private final AgentService agentService;
    private final AdminService adminService;
    public TransactionRequest createPaymentTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
        AgentResponse beneficiary = adminService.findAgentById(beneficiaryId);
        Client sender = agentService.getClientById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + senderId));

        return new TransactionRequest(
                null, // ID généré ultérieurement
                amount,
                beneficiary.id(),
                beneficiary.firstName()+" "+beneficiary.lastName(),
                beneficiary.phoneNumber(),
                "agent",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING, // Statut initial
                "MAD",
                null, // Pas de date de validation au début
                sender.getId(),
                sender.getFirstName()+" "+sender.getLastName(),
                sender.getPhoneNumber(),
                "client",
                sender.getCurrency()
        );
    }

    public TransactionRequest createTransferTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
        Client beneficiary = agentService.getClientById(beneficiaryId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + beneficiaryId));
        Client sender = agentService.getClientById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + senderId));

        return new TransactionRequest(
                null, // ID généré ultérieurement
                amount,
                beneficiary.getId(),
                beneficiary.getFirstName()+" "+beneficiary.getLastName(),
                beneficiary.getPhoneNumber(),
                "client",
                TransactionType.TRANSFER,
                TransactionStatus.PENDING, // Statut initial
                "MAD",
                null, // Pas de date de validation au début
                sender.getId(),
                sender.getFirstName()+" "+sender.getLastName(),
                sender.getPhoneNumber(),
                "client",
                sender.getCurrency()
        );
    }

    private final ClientRepository clientRepository;

    public String getClientIdByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findIdByPhoneNumber(phoneNumber);
        if (client != null) {
            return client.getId(); // Assurez-vous que getId() retourne l'ID sous forme de String
        } else {
            throw new RuntimeException("Aucun client trouvé avec le numéro de téléphone : " + phoneNumber);
        }
    }

    public Client getClientById(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    //-------------------------salwa-------------------------//
    // salwa here


    //-------------------------kawtar-------------------------//
    // kawtar here

    private final ClientRepository clientRepository;
    private final WalletClient walletClient;
    private final TransactionClient transactionClient;
    private final ServiceTiersClient serviceTiersClient;

    public WalletResponse getWalletByClientId(String clientId) {
        return walletClient.getWalletByIdClient(clientId).getBody();
    }
    public List<TransactionResponse> getTransactionsByUserId(String userId) {
        return transactionClient.getTransactionsByUser(userId);
    }
    public List<TransactionResponse> getAllTransactionsByUserId(String userId) {
        return transactionClient.getAllTransactionsByUserId(userId);
    }
    public boolean feedWallet(String clientId, double amount) {
        ResponseEntity<Boolean> response = serviceTiersClient.feedWallet(clientId, amount);
        return response.getBody() != null && response.getBody();
    }
}

