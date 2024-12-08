package com.example.user.users.service;

import com.example.user.users.entity.Client;
import com.example.user.users.repository.ClientRepository;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final ClientRepository clientRepository;
    private final WalletClient walletClient;


    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(String id, Client client) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            return clientRepository.save(client);
        }
        throw new RuntimeException("Client not found");
    }

    public void deleteClient(String id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Transactional
    public Client createClient(Client client) {
        Client savedClient = clientRepository.save(client);


        WalletRequest walletRequest = new WalletRequest(
                null,
                0.0,
                savedClient.getId()
        );
        try {
            ResponseEntity<String> walletResponse = walletClient.saveWallet(walletRequest);

            if (walletResponse.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Wallet created successfully for client ID: " + savedClient.getId());
            } else {
                System.out.println("Error creating wallet: " + walletResponse.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error when calling wallet service: " + e.getMessage());
            e.printStackTrace();
        }


        return savedClient;
    }
}
