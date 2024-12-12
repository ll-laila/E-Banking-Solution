package com.example.user.users.controller;

import com.example.user.transactionClient.SimpleTransactionRequest;
import com.example.user.transactionClient.TransactionClient;
import com.example.user.transactionClient.TransactionRequest;
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
import com.example.user.walletCryptoClient.TransactionResponse;
import com.example.user.walletCryptoClient.WalletCryptoClient;
import com.example.user.walletCryptoClient.WalletCryptoResponse;
import com.example.user.users.service.ClientService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private AdminService service;

    //--------------------------------------Admin-----------------------------------//

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


    // for updating password also
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


    @PostMapping("/client")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(agentService.createClient(clientRequest));
    }


    @GetMapping("/client/allClients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<Client> clients = agentService.getAllClients();
        List<ClientResponse> clientResponses = clients.stream()
                .map(clientMapper::fromClient)
                .toList();
        return ResponseEntity.ok(clientResponses);
    }


    @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        return clientOpt.map(client -> ResponseEntity.ok(clientMapper.fromClient(client)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable String id, @RequestBody ClientRequest clientRequest) {
        Optional<Client> existingClientOpt = agentService.getClientById(id);

        if (existingClientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Client clientToUpdate = clientMapper.toClient(clientRequest);
        clientToUpdate.setId(id);

        Client updatedClient = agentService.updateClient(id, clientToUpdate);

        ClientResponse clientResponse = clientMapper.fromClient(updatedClient);

        return ResponseEntity.ok(clientResponse);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        if (clientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        agentService.deleteClient(id); // Supprimer le client
        return ResponseEntity.noContent().build(); // Retourner le statut HTTP 204 No Content
    }



    //--------------------------------------Client-----------------------------------//

    //-------------------------laila-------------------------//
    @Autowired
    private WalletCryptoClient walletCryptoClient;

    @GetMapping("/walletCrypto/{userId}")
    public ResponseEntity<WalletCryptoResponse> getUserWalletCrypto(@PathVariable String userId){
        return  ResponseEntity.ok(walletCryptoClient.getWalletByUserId(userId).getBody());
    }

    @PostMapping("/buyCryptos")
    public ResponseEntity<String> buyCryptos(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount){
        return  ResponseEntity.ok(walletCryptoClient.buyCrypto(userId,cryptoName,amount));
    }

    @PostMapping("/setCryptosToSell")
    public ResponseEntity<String> setCryptosToSell(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount){
        return  ResponseEntity.ok(walletCryptoClient.sellCrypto(userId,cryptoName,amount));
    }


    @GetMapping("/allTransCrypro/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser){
        return  ResponseEntity.ok(walletCryptoClient.getUserTransaction(idUser).getBody());
    }

    @PostMapping("/transferCryptosToMoney")
    public ResponseEntity<String> transferCryptosToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount){
        return ResponseEntity.ok(walletCryptoClient.transferCryptoToMoney(userId,cryptoName,amount));
    }









    //-------------------------chaima-------------------------//
    private final ClientService clientService;
    private final TransactionClient transactionClient;
    @PostMapping("/creat-transaction")
    public ResponseEntity<String> createTransaction(@Valid @RequestBody SimpleTransactionRequest request ) {
        TransactionRequest transaction;

        switch (request.transactionType()) {
            case PAYMENT -> transaction = clientService.createPaymentTransaction(
                    request.senderId(),
                    request.beneficiaryId(),
                    request.amount()
            );

            case TRANSFER -> transaction = clientService.createTransferTransaction(
                    request.senderId(),
                    request.beneficiaryId(),
                    request.amount()
            );

            default -> {
                return ResponseEntity.badRequest().body("Transaction type not supported");
            }
        }

        try {
            transactionClient.createTransaction(transaction);
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).body("Failed to create transaction: " + ex.getMessage());
        }

        return ResponseEntity.ok("Transaction created successfully with type: " + transaction.transactionType());
    }



    //-------------------------salwa-------------------------//
    // salwa here


    //-------------------------kawtar-------------------------//
    // kawtar here
}
