package org.example.user.agent.service;

import lombok.RequiredArgsConstructor;
import org.example.user.agent.dto.AddClientRequest;
import org.example.user.agent.dto.ClientResponse;
import org.example.user.client.entity.Client;
import org.example.user.client.repository.ClientRepository;
import org.example.user.wallet.Wallet;
import org.example.user.wallet.WalletClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgentServiceImpl implements AgentService {

    private final ClientRepository clientRepository;
    private final WalletClient walletClient;

    public void addClient(AddClientRequest addClientRequest) {
        // 1. Créer un wallet via le client Feign
        Wallet wallet = Wallet.builder().balance(0D).build();
        ResponseEntity<Long> walletResponse = walletClient.saveWallet(wallet);

        // 2. Vérifier la réponse et récupérer l'ID du wallet
        if (!walletResponse.getStatusCode().is2xxSuccessful() || walletResponse.getBody() == null) {
            throw new RuntimeException("Failed to create wallet");
        }

        Long walletId = walletResponse.getBody();

        // 3. Créer un client en associant le walletId
        Client client = Client.builder()
                .CIN(addClientRequest.getCin())
                .email(addClientRequest.getEmail())
                .firstName(addClientRequest.getFirstName())
                .lastName(addClientRequest.getLastName())
                .address(addClientRequest.getAddress())
                .password("0000") // Mot de passe par défaut
                .walletId(walletId)
                .build();

        // 4. Sauvegarder le client dans le repository
        clientRepository.save(client);
    }

    @Override
    public List<ClientResponse> findAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> {
                    // Récupérer les détails du wallet via le client Feign
                    Long walletId = client.getWalletId();
                    Double balance = 0.0; // Solde par défaut
                    if (walletId != null) {
                        ResponseEntity<Double> walletResponse = walletClient.findWalletBalance(walletId);
                        if (walletResponse.getStatusCode().is2xxSuccessful() && walletResponse.getBody() != null) {
                            balance = walletResponse.getBody();
                        }
                    }

                    // Construire le ClientResponse
                    return ClientResponse.builder()
                            .firstName(client.getFirstName())
                            .lastName(client.getLastName())
                            .walletId(walletId)
                            .balance(balance)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
