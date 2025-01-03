package com.example.user.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Client {
    @Id
    private String id;
    private String agentId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cin;
    private Date birthDate ;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private Boolean isFirstLogin ;
    private Date createdDate;
    private String commercialRn;
    private String patentNumber ;
    private Boolean isPaymentAccountActivated ;
    private String typeHissab;
    private String currency;
    private Role role ;

}
