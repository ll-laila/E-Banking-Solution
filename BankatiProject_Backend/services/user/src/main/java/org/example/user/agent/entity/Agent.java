package org.example.user.agent.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cin;
    private Date birthDate ;
    private String newPassword;
    private String phoneNumber;
}
