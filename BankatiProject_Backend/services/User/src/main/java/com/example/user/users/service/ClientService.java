package com.example.user.users.service;


import com.example.user.transactionClient.TransactionRequest;
import com.example.user.transactionClient.TransactionStatus;
import com.example.user.transactionClient.TransactionType;
import com.example.user.users.entity.Client;
import com.example.user.users.entity.User;
import com.example.user.users.repository.ClientRepository;
import com.example.user.users.repository.UserRepository;
import com.example.user.users.response.AgentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.user.users.entity.Role.AGENT;
import static com.example.user.users.entity.Role.CLIENT;

@Service
@RequiredArgsConstructor
public class ClientService {


    //-------------------------laila-------------------------//
    // laila here




    //-------------------------chaima-------------------------//
    private final AgentService agentService;
    private final AdminService adminService;
    public TransactionRequest createPaymentTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
        User beneficiary = getClientById(beneficiaryId);
        User sender = getClientById(senderId);

        return new TransactionRequest(
                null, // ID généré ultérieurement
                amount,
                beneficiary.getId(),
                beneficiary.getFirstName()+" "+beneficiary.getLastName(),
                beneficiary.getPhoneNumber(),
                AGENT,
                TransactionType.PAYMENT,
                TransactionStatus.COMPLETED, // Statut initial
                beneficiary.getCurrency(),
                null, // Pas de date de validation au début
                sender.getId(),
                sender.getFirstName()+" "+sender.getLastName(),
                sender.getPhoneNumber(),
                CLIENT,
                sender.getCurrency()
        );
    }

    public TransactionRequest createTransferTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
        User beneficiary =getClientById(beneficiaryId);
        User sender =getClientById(senderId);
        return new TransactionRequest(
                null, // ID généré ultérieurement
                amount,
                beneficiary.getId(),
                beneficiary.getFirstName()+" "+beneficiary.getLastName(),
                beneficiary.getPhoneNumber(),
                CLIENT,
                TransactionType.TRANSFER,
                TransactionStatus.COMPLETED, // Statut initial
                beneficiary.getCurrency(),
                null, // Pas de date de validation au début
                sender.getId(),
                sender.getFirstName()+" "+sender.getLastName(),
                sender.getPhoneNumber(),
                CLIENT,
                sender.getCurrency()
        );
    }

    private final UserRepository clientRepository;

   /* public String getClientIdByPhoneNumber(String phoneNumber) {
        // Appeler la méthode du repository
        User client = clientRepository.findIdByPhoneNumber(phoneNumber);
        if (client != null) {
            return client.getId(); // Assurez-vous que getId() retourne l'ID sous forme de String
        } else {
            throw new RuntimeException("Aucun client trouvé avec le numéro de téléphone : " + phoneNumber);
        }
    }
*/
    public User getClientById(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    //-------------------------salwa-------------------------//
    // salwa here


    //-------------------------kawtar-------------------------//
    // kawtar here
}

