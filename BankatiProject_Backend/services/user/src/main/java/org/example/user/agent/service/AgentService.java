package org.example.user.agent.service;

import org.example.user.agent.dto.AddAmountToPortefeuilleRequest;
import org.example.user.agent.dto.AddClientRequest;
import org.example.user.agent.dto.ClientResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentService {
    void addClient(AddClientRequest addClientRequest);
    List<ClientResponse> findAllClients();
}
