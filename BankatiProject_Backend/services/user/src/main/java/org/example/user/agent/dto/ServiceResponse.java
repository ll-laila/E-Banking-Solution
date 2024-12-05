package org.example.user.agent.dto;


import lombok.Builder;

@Builder
public record ServiceResponse (
        Long id,
        String name,
         String type,
         AgentResponse agent
){
}
