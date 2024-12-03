package org.example.user.client.service;

import org.example.user.client.dto.ClientResponse;
import org.example.user.client.entity.Client;
import org.example.user.client.mapper.ClientMapper;
import org.example.user.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper :: fromClient)
                .collect(Collectors.toList());
    }

    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Client with ID %d not found", id))); // Gestion d'exception
        return clientMapper.fromClient(client);
    }


}
