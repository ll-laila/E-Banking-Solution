package org.example.user.agent.dto;


import lombok.Builder;

@Builder
public record ServiceRequest (
        Long id,
        String name,
        String type,
        AgentRequest agent
){
}
