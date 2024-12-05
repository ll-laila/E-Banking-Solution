package org.example.user.agent.dto;
import lombok.Builder;

@Builder
public record ClientRequest (
        String firstName,
        String lastName,
        String email,
        String address,
        String cin
){
}

