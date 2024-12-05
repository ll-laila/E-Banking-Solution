package org.example.user.agent.controller;

import org.example.user.agent.dto.ClientRequest;
import org.example.user.agent.dto.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.example.user.agent.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
@RequiredArgsConstructor
@CrossOrigin
public class AgentController {

    private final AgentService agentService;

    /**
     * Endpoint pour ajouter un nouveau client
     *
     * @param addClientRequest Contient les informations du client à ajouter
     * @return Une réponse HTTP avec le statut 201 (Created) si l'opération réussit
     */
    @PostMapping("/addClient")
    public ResponseEntity<Void> addClient(@RequestBody ClientRequest addClientRequest) {
        agentService.addClient(addClientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint pour récupérer la liste de tous les clients
     *
     * @return Une réponse HTTP contenant la liste des clients et le statut 200 (OK)
     */
    @GetMapping("/clients")
    public ResponseEntity<List<ClientResponse>> findAllClients() {
        List<ClientResponse> clients = agentService.findAllClients();
        return ResponseEntity.ok(clients);
    }
}
