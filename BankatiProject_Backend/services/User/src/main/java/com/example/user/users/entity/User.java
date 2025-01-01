package com.example.user.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class User  {


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

    private Role role ;
    private String password;

    @JsonIgnore
    private boolean isFirstLogin ;
    private Date createdDate;
    private String commercialRn;
    private String image;
    private String patentNumber ;
    private Boolean isPaymentAccountActivated ;
    private String typeHissab;
    private String currency;


}
