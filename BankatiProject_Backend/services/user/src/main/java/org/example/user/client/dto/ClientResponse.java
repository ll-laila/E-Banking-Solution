package org.example.user.client.dto;


import lombok.Builder;

@Builder
public record ClientResponse (

        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        String address,
        String CIN,
        Long walletId
){
}
