package com.example.user.users.dto;

import com.example.user.users.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

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
    private boolean isFirstLogin ;
    private Date createdDate;
    private String commercialRn;
    private String image;
    private String patentNumber ;
    private Boolean isPaymentAccountActivated ;
    private String typeHissab;
    private String currency;
    private String token;
    private String message;


}
