package com.example.user.users.service;


import com.example.user.users.entity.Admin;
import com.example.user.users.entity.Agent;
import com.example.user.users.entity.Role;
import com.example.user.users.mapper.AdminMapper;
import com.example.user.users.mapper.AgentMapper;
import com.example.user.users.repository.AdminRepository;
import com.example.user.users.repository.AgentRepository;
import com.example.user.users.request.AdminRequest;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.response.AgentResponse;
import com.example.user.users.twilio.Const;
import com.example.user.users.twilio.ENVConfig;
import com.example.user.users.twilio.TwilioConfiguration;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletRequest;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Slf4j
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

    @Autowired
    private WalletClient walletClient;


    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String CHARACTERS = "0123456789";

    private final TwilioConfiguration twilioConfiguration;
    private final ENVConfig envConfig;


    public Admin register(AdminRequest adminRequest) {
        Admin admin = AdminMapper.toAdmin(adminRequest);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ADMIN);// Encode password
        adminRepository.save(admin);
        return admin;
    }

    public Admin authenticate(String phoneNumber, String password) {
        Admin admin = adminRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return admin;
    }


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
                .currency(request.currency())
                .role(Role.AGENT)
                .build();

        String formattedPhoneNumber=formatPhoneNumber(request.phoneNumber());
        System.out.println(formattedPhoneNumber);
        System.out.println("generatedPassword" + generatedPassword);
        var savedAgent = agentRepository.save(agent);

        // add wallet
        WalletRequest wallet = new WalletRequest(null,0D,savedAgent.getId(),null);
        var idWallet = walletClient.saveWallet(wallet);

        //twilio
        String msg =   "Bonjour "+ request.firstName() +" "+request.lastName() + ", votre mot de passe est "+ generatedPassword + ".";
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",msg) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

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
        return "+212" + formatted;
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



    public AgentResponse updateAgent(AgentRequest request) {
        Optional<Agent> agent = agentRepository.findById(request.id());
        if(agent.isPresent()) {

           Agent agentUp =  Agent.builder()
                    .id(request.id())
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .address(request.address())
                    .cin(request.cin())
                    .birthDate(request.birthDate())
                    .commercialRn(request.commercialRn())
                    .image(request.image())
                    .password(passwordEncoder.encode(request.password())) //encode pwd after update !
                    .phoneNumber(request.phoneNumber())
                    .isFirstLogin(false)
                    .role(Role.AGENT)
                    .build();
            return agentMapper.fromAgent(agentRepository.save(agentUp));

        }else {
            System.out.println("The Agent with the Id Given not exist in the database ");
            return null;
        }

    }



    public AgentResponse findAgentById(String id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return agentMapper.fromAgent(agent);
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
