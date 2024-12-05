package org.example.user.wallet;


import lombok.Builder;

@Builder
public record Wallet (
        Long id,
        Double balance,
        Long clientId
){
}

