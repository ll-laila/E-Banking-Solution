package org.example.user.agent.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddAmountToPortefeuilleRequest {
    private Double amount;
    private String cin;
}




