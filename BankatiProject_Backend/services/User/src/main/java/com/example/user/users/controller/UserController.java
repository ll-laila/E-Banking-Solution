package com.example.user.users.controller;

import com.example.user.transactionClient.*;
import com.example.user.users.entity.Admin;
import com.example.user.users.entity.AgentServiceRequest;
import com.example.user.users.entity.Client;
import com.example.user.users.entity.ServiceAgentResponse;
import com.example.user.users.mapper.ClientMapper;
import com.example.user.users.request.AdminRequest;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.request.ClientRequest;
import com.example.user.users.response.AgentResponse;
import com.example.user.users.response.ClientResponse;
import com.example.user.users.service.AdminService;
import com.example.user.users.service.AgentService;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletResponse;
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

import java.math.BigDecimal;
import java.util.List;
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

    @PostMapping("/service/{id}")
    //@PreAuthorize("hasAuthority('agent:create')")
    //@Hidden
    public ResponseEntity<ServiceAgentResponse> createService(@PathVariable String id, @RequestBody AgentServiceRequest request) {
        return ResponseEntity.ok(agentService.createService(request, id));
    }


    @PutMapping("/service/{serviceId}")
    //@PreAuthorize("hasAuthority('agent:update')")
    public ResponseEntity<ServiceAgentResponse> updateService(
            @PathVariable String serviceId,
            @RequestBody AgentServiceRequest request
    ) {
        return ResponseEntity.ok(agentService.updateService(serviceId, request));
    }
/*
    @DeleteMapping("/service/{serviceId}")
    //@PreAuthorize("hasAuthority('agent:delete')")
    public RegisterAgentResponse deleteService(@PathVariable Long serviceId) {
        return agentservice.deleteService(serviceId);
    }

 */
    @GetMapping("/serviceByAgent/{agentId}")
    //@PreAuthorize("hasAuthority('agent:read')")
    public List<AgentServiceRequest> getServicesByAgent(@PathVariable("agentId") String agentId) {
        return agentService.getAllServicesByAgentId(agentId);
    }


    //--------------------------------------Client-----------------------------------//

    //-------------------------laila-------------------------//
    @Autowired
    private WalletCryptoClient walletCryptoClient;
    @Autowired
    private WalletClient walletUser;


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

    @GetMapping("/getActualPrice/{cryptoName}")
    public ResponseEntity<Double> getPriceCrypto(@PathVariable("cryptoName") String cryptoName){
        return ResponseEntity.ok(walletCryptoClient.getPriceCrypto(cryptoName));
    }


    @GetMapping("/userWalletBalance/{clientId}")
    public ResponseEntity<Double> getBalanceWalletByIdClient(@PathVariable("clientId") String clientId){
        return ResponseEntity.ok(walletUser.getWalletByIdClient(clientId).getBody().balance());
    }




    //-------------------------chaima-------------------------//
    private final ClientService clientService;
    private final TransactionClient transactionClient;

    @PostMapping("/creat-transaction")
    public ResponseEntity<String> createTransaction(@RequestParam String senderId, @RequestParam String beneficiaryId, @RequestParam BigDecimal amount, @RequestParam TransactionType transactionType) {
        TransactionRequest transaction;

        switch (transactionType) {
            case PAYMENT -> transaction = clientService.createPaymentTransaction(
                   senderId,
                    beneficiaryId,
                    amount
            );

            case TRANSFER -> transaction = clientService.createTransferTransaction(
                    senderId,
                    beneficiaryId,
                    amount
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

    @PostMapping("/creat-subscription")
    public ResponseEntity<String> createSubscription(@RequestParam  String userId,
                                                     @RequestParam String agentId,
                                                     @RequestParam BigDecimal price,
                                                     @RequestParam int durationInMonths) {
        SubscriptionRequest subscriptionRequest= new SubscriptionRequest(userId,agentId,price,durationInMonths);
        try {
            transactionClient.createSubscription(subscriptionRequest);
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).body("Failed to create subscription: " + ex.getMessage());
        }
        return ResponseEntity.ok("Subscription created successfully");
    }


    @GetMapping("/clientByPhone/{phoneNumber}")
    public ResponseEntity<String> getClientIdByPhoneNumber(@PathVariable String phoneNumber) {
        String clientId = clientService.getClientIdByPhoneNumber(phoneNumber);
        if (clientId != null) {
            return ResponseEntity.ok(clientId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client introuvable");
        }
    }

    @GetMapping("/clientbyid/{clientId}")
    public ResponseEntity<Client> getClientInfo(@PathVariable("clientId") String clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    //-------------------------salwa-------------------------//
    // salwa here


    //-------------------------kawtar-------------------------//
    // kawtar here
}
