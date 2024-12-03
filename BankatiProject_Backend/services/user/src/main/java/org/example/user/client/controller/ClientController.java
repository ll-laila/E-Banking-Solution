package org.example.user.client.controller;


import lombok.RequiredArgsConstructor;
import org.example.user.client.dto.ClientResponse;
import org.example.user.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> findById(
            @PathVariable("clientId") Long userId)
    {
        return ResponseEntity.ok(clientService.getClientById(userId));
    }

    @GetMapping("/allClients")
    public ResponseEntity<List<ClientResponse>> findAllClient()
    {
        return ResponseEntity.ok(clientService.getAllClients());
    }



}
