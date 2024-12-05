package org.example.user.agent.Mapper;

import org.example.user.agent.dto.AgentRequest;
import org.example.user.agent.dto.AgentResponse;
import org.example.user.agent.entity.Agent;
import org.springframework.stereotype.Service;




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
                .password(request.password())
                .phoneNumber(request.phoneNumber())
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
                agent.getPassword(),
                agent.getPhoneNumber()

        );


    }

}
