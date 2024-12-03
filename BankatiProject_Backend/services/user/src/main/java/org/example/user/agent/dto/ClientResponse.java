package org.example.user.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientResponse {
    private String firstName;
    private String lastName;
    private Long walletId;
    private Double balance;
}
