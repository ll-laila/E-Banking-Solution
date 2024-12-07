package com.example.user.users.service;


import com.example.user.users.entity.Admin;
import com.example.user.users.entity.Agent;
import com.example.user.users.mapper.AdminMapper;
import com.example.user.users.mapper.AgentMapper;
import com.example.user.users.repository.AdminRepository;
import com.example.user.users.repository.AgentRepository;
import com.example.user.users.request.AdminRequest;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.response.AgentResponse;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentMapper agentMapper;

    private static final String CHARACTERS = "0123456789";


    @Autowired
    private PasswordEncoder passwordEncoder;


    //--------------------------------------Admin-----------------------------------//

    public Admin saveAdmin(AdminRequest request) {
        return adminRepository.save(adminMapper.toAdmin(request));
    }

    //--------------------------------------Agent-----------------------------------//


    public AgentResponse addAgent(AgentRequest request) {
        if (agentRepository.existsByPhoneNumber(request.phoneNumber()) && agentRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Phone num already exists Or Email");
        }

        String generatedPassword =generatePassword();
        var agent = Agent.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .cin(request.cin())
                .birthDate(request.birthDate())
                .phoneNumber(request.phoneNumber())
                .commercialRn(request.commercialRn())
                .image(request.image())
                .password(passwordEncoder.encode(generatedPassword))
                .isFirstLogin(true)
                .build();

        String formattedPhoneNumber=formatPhoneNumber(request.phoneNumber());
        System.out.println(formattedPhoneNumber);
        var savedAgent = agentRepository.save(agent);

        // vonage
        VonageClient client = VonageClient.builder().apiKey("2053ed34").apiSecret("j2Cy3qjnDhKlnCbi").build();
        System.out.println(client);
        TextMessage message = new TextMessage("BANKATI LKLCSF",
                formattedPhoneNumber,
                "Bonjour "+ request.firstName() +" "+request.lastName() + ", votre mot de passe est "+ generatedPassword + "."
        );
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }

        return agentMapper.fromAgent(savedAgent);
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

    public String formatPhoneNumber(String phoneNumber) {

        String formatted = phoneNumber.substring(1);
        return "212" + formatted;
    }



    public AgentResponse findAgentById(String id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return agentMapper.fromAgent(agent);
    }


    public AgentResponse updateAgent(AgentRequest updateRequest) {

        return null;
    }


    public void delete(String id) {
        agentRepository.deleteById(id);
    }

    public List<AgentResponse> findAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(agentMapper :: fromAgent)
                .collect(Collectors.toList());
    }


}
