package org.example.user.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddClientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cin;
}

