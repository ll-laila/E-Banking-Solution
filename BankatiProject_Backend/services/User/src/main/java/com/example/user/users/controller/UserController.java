package com.example.user.users.controller;


import com.example.user.transactionClient.*;
import com.example.user.depenseClient.DepenseClient;
import com.example.user.depenseClient.DepenseRequest;
import com.example.user.depenseClient.DepenseResponse;
import com.example.user.transactionClient.SimpleTransactionRequest;
import com.example.user.transactionClient.SubscriptionRequest;
import com.example.user.transactionClient.TransactionClient;
import com.example.user.transactionClient.TransactionRequest;
import com.example.user.transactionClient.TransactionType;

import com.example.user.users.config.UserAuthenticationProvider;
import com.example.user.users.dto.CredentialsDto;
import com.example.user.users.dto.SignUpDto;
import com.example.user.users.dto.UserDto;
import com.example.user.users.entity.*;
import com.example.user.users.mapper.ClientMapper;
import com.example.user.users.request.*;
import com.example.user.users.response.AgentResponse;
import com.example.user.users.response.ClientResponse;
import com.example.user.users.response.ServiceResponse;
import com.example.user.users.response.UserResponse;
import com.example.user.users.service.AdminService;
import com.example.user.users.service.AgentService;
import com.example.user.users.service.UserService;
import com.example.user.virtualCardClient.VirtualCardClient;
import com.example.user.virtualCardClient.VirtualCardResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private AdminService service;

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;




    //login for all users (admin, agent, client)
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        // Authenticate user
        UserDto userDto = userService.login(credentialsDto);

        log.info("Creating token for phone number: {}", userDto.getPhoneNumber());

        // Generate token with phone number
        String token = userAuthenticationProvider.createToken(userDto.getPhoneNumber());

        // Set token in response
        userDto.setToken(token);

        return ResponseEntity.ok(userDto);
    }

    //admin registry
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getPhoneNumber()));
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest request) {
        userService.changePassword(request.phoneNumber(), request.oldPassword(), request.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @PostMapping("/create-client")
    public ResponseEntity<String> createClient(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createClient(userRequest));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    @PutMapping("/deactivate-account/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable String id) {
        boolean isDeactivated = userService.deactivateAccount(id);
        if (isDeactivated) {
            return ResponseEntity.ok("Compte désactivé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de la désactivation du compte.");
        }
    }


    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @GetMapping("/clients")
    public ResponseEntity<List<UserResponse>> getAllClients() {
        return ResponseEntity.ok(userService.getAllClients());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping("/agents")
    public ResponseEntity<List<User>> getAllAgents() {
        return ResponseEntity.ok(userService.getAllAgents());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-agent")
    public ResponseEntity<String> createAgent(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createAgent(userRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/getUserByPhone/{phone}")
    public ResponseEntity<String> getUserIdByPhone(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(userService.getClientIdByPhoneNumber(phone));
    }

    //--------------------------------------Admin-----------------------------------//

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody AdminRequest request) {
        return ResponseEntity.ok(service.saveAdmin(request));
    }



    @GetMapping("/getAgent/{id}")
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findAgentById(id));
    }


    // for updating password also
    @PutMapping("/agent/updateAgent/{id}")
    public ResponseEntity<AgentResponse> updateUser(@RequestBody AgentRequest updatedAgent) {
        return ResponseEntity.ok(service.updateAgent(updatedAgent));
    }



    /*@DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String id)  {
        service.delete(id);
    }


    @GetMapping("/listAgent")
    public ResponseEntity<List<AgentResponse>> getAllAgents() {
        return ResponseEntity.ok( service.findAllAgents());
    }*/


    //--------------------------------------Agent-----------------------------------//



   /* @PostMapping("/client")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(agentService.createClient(clientRequest));
    }
*/
    /*@PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @GetMapping("/client/allClients")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<Client> clients = agentService.getAllClients();
        List<ClientResponse> clientResponses = clients.stream()
                .map(clientMapper::fromClient)
                .toList();
        return ResponseEntity.ok(clientResponses);
    }*/


  /*  @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        return clientOpt.map(client -> ResponseEntity.ok(clientMapper.fromClient(client)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
*/
    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping("/client/{id}")
    public ResponseEntity<UserResponse> getClientById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));

    }


   /* @PutMapping("/client/{id}")
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

    */

   /* @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        Optional<Client> clientOpt = agentService.getClientById(id);

        if (clientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        agentService.deleteClient(id); // Supprimer le client
        return ResponseEntity.noContent().build(); // Retourner le statut HTTP 204 No Content
    }

    */
    @PreAuthorize("hasRole('AGENT')")
    @PostMapping("/creatService")
    public ResponseEntity<ServiceResponse> createService(@RequestBody Service request) {
        System.out.println("Requête reçue pour créer un service.");
        return ResponseEntity.ok(userService.createService(request));
    }

    @PreAuthorize("hasRole('AGENT')")
    @PutMapping("/service/{serviceId}")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable String serviceId,
            @RequestBody Service request
    ) {
        return ResponseEntity.ok(userService.updateService(serviceId, request));
    }

    @PreAuthorize("hasRole('AGENT') ")
    @GetMapping("/serviceByAgent/{agentId}")
    public List<Service> getServicesByAgent(@PathVariable("agentId") String agentId) {
        return userService.getAllServicesByAgentId(agentId);
    }

    @PreAuthorize("hasRole('AGENT') or hasRole('CLIENT') ")
    @GetMapping("/serviceById/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable String id) {
        try {
            // Appel de la méthode pour récupérer le service
            com.example.user.users.entity.Service service = userService.getServiceById(id);

            if (service == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(service);
        } catch (Exception e) {
            // En cas d'erreur, retourne une réponse 500 avec le message d'erreur
            return ResponseEntity.status(500).body("Erreur lors de la récupération du service : " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('AGENT') ")
    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id) {
        try {
            // Appel de la méthode pour supprimer le service
            ServiceAgentResponse response = userService.deleteService(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Retourner une réponse 404 si le service n'est pas trouvé
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            // Retourner une réponse 500 pour les erreurs générales
            return ResponseEntity.status(500).body("Erreur lors de la suppression du service : " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('AGENT') ")
    @GetMapping("/clientsByAgent/{agentId}")
    public List<UserResponse> getClientsByAgentId(@PathVariable ("agentId") String agentId) {
        return userService.getAllClientsByAgentId(agentId);
    }
    //--------------------------------------Client-----------------------------------//

    //-------------------------laila-------------------------//
    @Autowired
    private WalletCryptoClient walletCryptoClient;
    @Autowired
    private WalletClient walletUser;


    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/walletCrypto/{userId}")
    public ResponseEntity<WalletCryptoResponse> getUserWalletCrypto(@PathVariable String userId) {
        return ResponseEntity.ok(walletCryptoClient.getWalletByUserId(userId).getBody());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/buyCryptos")
    public ResponseEntity<String> buyCryptos(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount) {
        return ResponseEntity.ok(walletCryptoClient.buyCrypto(userId, cryptoName, amount));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/setCryptosToSell")
    public ResponseEntity<String> setCryptosToSell(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount) {
        return ResponseEntity.ok(walletCryptoClient.sellCrypto(userId, cryptoName, amount));
    }


    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/allTransCrypro/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser) {
        return ResponseEntity.ok(walletCryptoClient.getUserTransaction(idUser).getBody());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/transferCryptosToMoney")
    public ResponseEntity<String> transferCryptosToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount) {
        return ResponseEntity.ok(walletCryptoClient.transferCryptoToMoney(userId, cryptoName, amount));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getActualPrice/{cryptoName}")
    public ResponseEntity<Double> getPriceCrypto(@PathVariable("cryptoName") String cryptoName) {
        return ResponseEntity.ok(walletCryptoClient.getPriceCrypto(cryptoName));
    }


    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/userWalletBalance/{clientId}")
    public ResponseEntity<Double> getBalanceWalletByIdClient(@PathVariable("clientId") String clientId) {
        return ResponseEntity.ok(walletUser.getWalletByIdClient(clientId).getBody().balance());
    }


    //-------------------------chaima-------------------------//
    private final ClientService clientService;
    private final TransactionClient transactionClient;

    @PreAuthorize("hasRole('CLIENT') ")
    @GetMapping("/service/{agentId}")
    public List<Service> getServiceByAgent(@PathVariable("agentId") String agentId) {
        return userService.getAllServicesByAgentId(agentId);
    }

    @PreAuthorize("hasRole('CLIENT')")
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
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/creat-subscription")
    public ResponseEntity<String> createSubscription(@RequestParam String userId,
                                                     @RequestParam String agentId,
                                                     @RequestParam BigDecimal price,
                                                     @RequestParam int durationInMonths) {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(userId, agentId, price, durationInMonths);
        try {
            transactionClient.createSubscription(subscriptionRequest);
        } catch (FeignException ex) {
            return ResponseEntity.status(ex.status()).body("Failed to create subscription: " + ex.getMessage());
        }
        return ResponseEntity.ok("Subscription created successfully");
    }

    /*@GetMapping("/clientByPhone/{phoneNumber}")
    public ResponseEntity<String> getClientIdByPhoneNumber(@PathVariable String phoneNumber) {
        String clientId = clientService.getClientIdByPhoneNumber(phoneNumber);
        if (clientId != null) {
            return ResponseEntity.ok(clientId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client introuvable");
        }
    }*/
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/clientbyid/{clientId}")
    public ResponseEntity<User> getClientInfo(@PathVariable("clientId") String clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable("userId") String userId) {
        try {
            ResponseEntity<List<Subscription>> response = transactionClient.getUserSubscriptions(userId);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // En cas d'erreur, renvoie une réponse 500.
        }
    }

    //-------------------------salwa-------------------------//
    // salwa here


    //-------------------------kawtar-------------------------//
    // kawtar here

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/wallet/{clientId}")
    public ResponseEntity<WalletResponse> getWalletInfo(@PathVariable("clientId") String clientId) {
        WalletResponse wallet = userService.getWalletByUserId(clientId);
        return ResponseEntity.ok(wallet);
    }



    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<com.example.user.transactionClient.TransactionResponse>> getUserTransactions(
            @PathVariable("userId") String userId) {
        List<com.example.user.transactionClient.TransactionResponse> transactions = userService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }


    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/feed-wallet")
    public ResponseEntity<Boolean> feedWallet(@RequestBody Map<String, Object> requestBody) {
        String clientId = (String) requestBody.get("clientId");
        double amount = ((Number) requestBody.get("amount")).doubleValue();

        boolean result = userService.feedWallet(clientId, amount);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/all-transactions/{userId}")
    public ResponseEntity<List<com.example.user.transactionClient.TransactionResponse>> getAllTransactionsByUserId(
            @PathVariable("userId") String userId) {
        List<com.example.user.transactionClient.TransactionResponse> transactions = userService.getAllTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }



    @Autowired
    private VirtualCardClient virtualCardClient;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/virtualcard/create/{userId}")
    public ResponseEntity<VirtualCardResponse> addVirtualCard(@PathVariable String userId) {
        return ResponseEntity.ok(virtualCardClient.createCard(userId));
    }
    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/activate/{cardId}")
    public ResponseEntity<VirtualCardResponse> activateCard(@PathVariable String cardId){
        return ResponseEntity.ok(virtualCardClient.createCard(cardId));
    }
    @PreAuthorize("hasRole('CLIENT')")
    @PatchMapping("/deactivate/{cardId}")
    public ResponseEntity<VirtualCardResponse> deactivateCard(@PathVariable String cardId){
        return ResponseEntity.ok(virtualCardClient.createCard(cardId));
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/virtualcard/user/{userId}")
    public ResponseEntity<VirtualCardResponse> getCardsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(virtualCardClient.getCardsByUser(userId));
    }


   @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/feed-card/{userId}/{somme}")
    public ResponseEntity<VirtualCardResponse> feedCard(@PathVariable String userId, @PathVariable Double somme) {
        //String clientId = (String) requestBody.get("clientId");
        //double somme = ((Number) requestBody.get("somme")).doubleValue();

        VirtualCardResponse result = virtualCardClient.feedCard(userId, somme);
        return ResponseEntity.ok(result);
    }


    /*kaoutar*/
    @Autowired
    private DepenseClient depenseClient;
    /**
     * Endpoint pour créer une nouvelle dépense.
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/create-depense")
    public ResponseEntity<DepenseResponse> createDepense(@RequestParam String userId, @RequestParam String userPhone, @RequestParam Double montant) {
        return depenseClient.createDepense(userId,userPhone,montant);
    }

    /**
     * Endpoint pour mettre à jour une dépense existante.
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/update-depense/{depenseId}")
    public ResponseEntity<DepenseResponse> updateDepense(@PathVariable String depenseId,
                                                         @RequestParam Double nouveauMontant) {
        return depenseClient.updateDepense(depenseId, nouveauMontant);
    }

    /**
     * Endpoint pour annuler une dépense.
     */
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/cancel-depense/{depenseId}")
    public ResponseEntity<DepenseResponse> cancelDepense(@PathVariable String depenseId) {
        return depenseClient.cancelDepense(depenseId);
    }

    /**
     * Endpoint pour récupérer une dépense par son ID.
     */
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/get-depense/{depenseId}")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable String depenseId) {
        return depenseClient.getDepenseById(depenseId);
    }

    /**
     * Endpoint pour récupérer toutes les dépenses d'un utilisateur par son ID.
     */
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping("/list-depense")
    public ResponseEntity<List<DepenseResponse>> getAllDepensesByUser(@RequestParam String userId) {
        return depenseClient.getAllDepensesByUser(userId);
    }
}
