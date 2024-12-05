package org.example.user.agent.dto;
import lombok.Builder;

import java.util.Date;

@Builder
public record AgentRequest (
        Long id,
        String firstName,
        String lastName,
        String email,
        String address,
        String cin,
        Date birthDate ,
        String password,
        String phoneNumber
){
}

