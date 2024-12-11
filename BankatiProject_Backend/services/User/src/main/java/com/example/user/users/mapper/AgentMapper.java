package com.example.user.users.mapper;

import com.example.user.users.entity.Agent;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.response.AgentResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AgentMapper {
    public Agent toAgent(AgentRequest request) {
        if (request == null) {
            return null;
        }
        return Agent.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .cin(request.cin())
                .birthDate(request.birthDate())
                .commercialRn(request.commercialRn())
                .image(request.image())
                .password(request.password())
                .phoneNumber(request.phoneNumber())
                .isFirstLogin(request.isFirstLogin())
                .currency(request.currency())
                .build();

    }

    public AgentResponse fromAgent(Agent agent) {

        return new AgentResponse(
                agent.getId(),
                agent.getFirstName(),
                agent.getLastName(),
                agent.getEmail(),
                agent.getAddress(),
                agent.getCin(),
                agent.getBirthDate(),
                agent.getCommercialRn(),
                agent.getImage(),
                agent.getPassword(),
                agent.getPhoneNumber(),
                agent.isFirstLogin(),
                agent.getCurrency()
        );


    }
}
