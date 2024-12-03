package org.example.user.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.portefeuille.Portefeuille;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String CIN;
    private String portefeuilleId;
}
