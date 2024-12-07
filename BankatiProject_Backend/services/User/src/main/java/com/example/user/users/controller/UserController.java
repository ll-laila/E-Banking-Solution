package com.example.user.users.controller;

import com.example.user.users.entity.Admin;
import com.example.user.users.entity.Client;
import com.example.user.users.mapper.ClientMapper;
import com.example.user.users.request.AdminRequest;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.request.ClientRequest;
import com.example.user.users.response.AgentResponse;
import com.example.user.users.response.ClientResponse;
import com.example.user.users.service.AdminService;
import com.example.user.users.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private AdminService service;


    //--------------------------------------Admin-----------------------------------//
    @GetMapping("/msg")
    public ResponseEntity<String> getAgentById() {
        return ResponseEntity.ok("hello");
    }


    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody AdminRequest request) {
        return ResponseEntity.ok(service.saveAdmin(request));
    }

    @PostMapping("/addAgent")
    public ResponseEntity<AgentResponse> AddAgent(@RequestBody AgentRequest request){
        return ResponseEntity.ok(service.addAgent(request));
    }



    @GetMapping("/getAgent/{id}")
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findAgentById(id));
    }


    @PutMapping("/updateAgent/{id}")
    public ResponseEntity<AgentResponse> updateUser(@RequestBody AgentRequest updatedAgent){
        return ResponseEntity.ok(service.updateAgent(updatedAgent));
    }



    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String id)  {
        service.delete(id);
    }


    @GetMapping("/listAgent")
    public ResponseEntity<List<AgentResponse>> getAllAgents() {
        return ResponseEntity.ok( service.findAllAgents());
    }


    //--------------------------------------Agent-----------------------------------//

    private final AgentService agentService;
    private final ClientMapper clientMapper;

    // Endpoint pour créer un nouveau client
    @PostMapping("/addClient")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        Client client = clientMapper.toClient(clientRequest);
        Client savedClient = agentService.createClient(client);

        // Vérifier que le client a été ajouté
        if (savedClient != null) {
            ClientResponse clientResponse = clientMapper.fromClient(savedClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse); // Status 201
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Si une erreur survient
        }
    }

    // Endpoint pour récupérer tous les clients
    @GetMapping("/getClients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<Client> clients = agentService.getAllClients();
        List<ClientResponse> clientResponses = clients.stream()
                .map(clientMapper::fromClient)
                .toList();
        return ResponseEntity.ok(clientResponses);
    }

    // Endpoint pour récupérer un client par son ID
    @GetMapping("/getClient/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        return clientOpt.map(client -> ResponseEntity.ok(clientMapper.fromClient(client)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint pour mettre à jour un client par son ID
    @PutMapping("/updateClient/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable String id, @RequestBody ClientRequest clientRequest) {
        Optional<Client> existingClientOpt = agentService.getClientById(id);

        if (existingClientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Convertir le ClientRequest en entité Client
        Client clientToUpdate = clientMapper.toClient(clientRequest);
        clientToUpdate.setId(id); // Assurer que l'ID reste intact

        // Mettre à jour le client
        Client updatedClient = agentService.updateClient(id, clientToUpdate);

        // Convertir l'entité Client mise à jour en ClientResponse
        ClientResponse clientResponse = clientMapper.fromClient(updatedClient);

        return ResponseEntity.ok(clientResponse);
    }

    // Endpoint pour supprimer un client par son ID
    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        if (clientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        agentService.deleteClient(id); // Supprimer le client
        return ResponseEntity.noContent().build(); // Retourner le statut HTTP 204 No Content
    }



    //--------------------------------------Client-----------------------------------//



}
