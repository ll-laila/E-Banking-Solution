package org.example.user.client.dto;

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
