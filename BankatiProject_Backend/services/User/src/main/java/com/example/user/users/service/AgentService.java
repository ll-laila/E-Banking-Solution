package com.example.user.users.service;

import com.example.user.users.entity.AgentServiceRequest;
import com.example.user.users.entity.Client;
import com.example.user.users.entity.ServiceAgentResponse;
import com.example.user.users.mapper.AgentServiceMapper;
import com.example.user.users.mapper.ClientMapper;
import com.example.user.users.repository.AgentRepository;
import com.example.user.users.repository.ClientRepository;
import com.example.user.users.repository.ServiceRepository;
import com.example.user.users.request.ClientRequest;
import com.example.user.users.response.ClientResponse;
import com.example.user.users.twilio.Const;
import com.example.user.users.twilio.ENVConfig;
import com.example.user.users.twilio.TwilioConfiguration;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletRequest;
import com.example.user.walletCryptoClient.WalletCryptoClient;
import com.example.user.walletCryptoClient.WalletCryptoRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {

    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final WalletClient walletClient;
    private final WalletCryptoClient walletCryptoClient;
    private final ENVConfig envConfig;
    private final AgentRepository agentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientMapper clientMapper;

    private static final String CHARACTERS = "0123456789";

    private final TwilioConfiguration twilioConfiguration;


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

    public Object sendSms(String phoneNumber, String message) {

        if (phoneNumber!=null) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(envConfig.getTwilioSmsFromNo());
            return Message.creator(to,from,message).create();
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + phoneNumber + "] is not a valid number"
            );
        }

    }
    public String formatPhoneNumber(String phoneNumber) {

        String formatted = phoneNumber.substring(1);
        return "+212" + formatted;
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    /*@Transactional
    public ClientResponse createClient(ClientRequest clientRequest) {
        if (clientRepository.existsByPhoneNumber(clientRequest.phoneNumber()) && clientRepository.existsByEmail(clientRequest.email())) {
            throw new RuntimeException("Phone num already exists Or Email");
        }

        String generatedPassword =generatePassword();
        var client = Client.builder()
                .agentId(clientRequest.agentId())
                .firstName(clientRequest.firstName())
                .lastName(clientRequest.lastName())
                .email(clientRequest.email())
                .address(clientRequest.address())
                .cin(clientRequest.cin())
                .birthDate(clientRequest.birthDate())
                .phoneNumber(clientRequest.phoneNumber())
                .password(passwordEncoder.encode(generatedPassword))
                .isFirstLogin(true)
                .createdDate(new Date())
                .commercialRn(clientRequest.commercialRn())
                .patentNumber(clientRequest.patentNumber())
                .isPaymentAccountActivated(true)
                .typeHissab(clientRequest.typeHissab())
                .currency(clientRequest.currency())
                .build();

        String formattedPhoneNumber=formatPhoneNumber(clientRequest.phoneNumber());
        System.out.println(formattedPhoneNumber);
        var savedClient = clientRepository.save(client);

        // add Crypto wallet
        Map<String, Double> cryptos = new HashMap<>();
        cryptos.put("bitcoin", 0.0);
        cryptos.put("ethereum", 0.0);
        WalletCryptoRequest walletCrypto = new WalletCryptoRequest(null,savedClient.getId(),cryptos);
        var idWalletCrypto = walletCryptoClient.saveWalletCrypto(walletCrypto);

        // add wallet
        WalletRequest wallet = new WalletRequest(null,1000D,savedClient.getId(),null);
        var idWallet = walletClient.saveWallet(wallet);


        //twilio
        String msg =   "Bonjour "+ client.getFirstName() +" "+client.getLastName() + ", votre mot de passe est "+ generatedPassword+ ".";
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",msg) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

        return clientMapper.fromClient(savedClient);
    }

     */

   /* public ServiceAgentResponse createService(AgentServiceRequest request, String id ) {
        // Trouver l'agent par son numéro de téléphone
//
//        Agent agent = agentRepository.findAgentByClientId(id);
//
//
//        // Vérifier si l'agent existe
//        if (agent == null) {
//            // Gérer le cas où l'agent n'est pas trouvé
//            throw new RuntimeException("Agent not found");
//        }
//
//        // Créer le service avec l'agent trouvé
//        AgentServiceRequest agentService = request;

        // Enregistrer le service
        serviceRepository.save(request);

        // Retourner la réponse
        return ServiceAgentResponse.builder().message("Service created successfully").build();
    }

    public List<AgentServiceRequest> getAllServicesByAgentId(String agentId) {
        List<AgentServiceRequest> servicesAgent = serviceRepository.findAllByAgentId(agentId);
        return servicesAgent.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    */

//    public AgentServiceResponse getServiceById(Long id) {
//        return serviceRepository.findById(id)
//                .map(AgentServiceMapper::ConvertToDto)
//                .orElse(null);
//    }

   /* public ServiceAgentResponse updateService(String serviceId, AgentServiceRequest request) {
        AgentServiceRequest agentService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        agentService.setName(request.getName());
        agentService.setType(request.getType());

        serviceRepository.save(agentService);

        return ServiceAgentResponse.builder().message("Service updated successfully").build();
    }

    */

    /*public ServiceAgentResponse deleteService(String serviceId) {
        AgentServiceRequest agentService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        serviceRepository.delete(agentService);

        return ServiceAgentResponse.builder().message("Service deleted successfully").build();
    }

     */


}
