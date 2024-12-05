package org.example.user.client.dto;


import lombok.Builder;

@Builder
public record ClientRequest (
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        String address,
        String CIN
){
}
