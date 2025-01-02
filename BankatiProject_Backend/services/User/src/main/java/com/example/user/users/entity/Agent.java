package com.example.user.users.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Agent {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String cin;
    private Date birthDate ;
    private String commercialRn;
    private String image;
    private String password;
    private String phoneNumber;
    private boolean isFirstLogin;
    private String currency;
    private Role role;

}
