package com.example.user.users.service;

import com.example.user.users.entity.Agent;
import com.example.user.users.entity.Client;
import com.example.user.users.mapper.ClientMapper;
import com.example.user.users.repository.ClientRepository;
import com.example.user.users.request.ClientRequest;
import com.example.user.users.response.ClientResponse;
import com.example.user.users.twilio.Const;
import com.example.user.users.twilio.ENVConfig;
import com.example.user.users.twilio.TwilioConfiguration;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.user.users.service.AdminService.generatePassword;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {

    private final ClientRepository clientRepository;
    private final WalletClient walletClient;
    private final ENVConfig envConfig;

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


    @Transactional
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
                .isFirstLogin(clientRequest.isFirstLogin())
                .createdDate(clientRequest.createdDate())
                .commercialRn(clientRequest.commercialRn())
                .patentNumber(clientRequest.patentNumber())
                .isPaymentAccountActivated(clientRequest.isPaymentAccountActivated())
                .typeHissab(clientRequest.typeHissab())
                .build();

        String formattedPhoneNumber=formatPhoneNumber(clientRequest.phoneNumber());
        System.out.println(formattedPhoneNumber);
        var savedClient = clientRepository.save(client);

        // add wallet
        WalletRequest wallet = new WalletRequest(null,1000D,savedClient.getId(),"MAD");
        var idWallet = walletClient.saveWallet(wallet);


        //twilio
        String msg =   "Bonjour "+ client.getFirstName() +" "+client.getLastName() + ", votre mot de passe est "+ client.getPassword() + ".";
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",msg) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

        return clientMapper.fromClient(savedClient);
    }


}
