package com.example.user.users.mapper;

import com.example.user.users.entity.Client;
import com.example.user.users.request.ClientRequest;
import com.example.user.users.response.ClientResponse;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

    public Client toClient(ClientRequest request) {
        if (request == null) {
            return null;
        }
        return Client.builder()
                .id(request.id())
                .agentId(request.agentId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .cin(request.cin())
                .birthDate(request.birthDate())
                .phoneNumber(request.phoneNumber())
                .password(request.password())
                .isFirstLogin(request.isFirstLogin())
                .createdDate(request.createdDate())
                .commercialRn(request.commercialRn())
                .patentNumber(request.patentNumber())
                .isPaymentAccountActivated(request.isPaymentAccountActivated())
                .typeHissab(request.typeHissab())
                .currency(request.currency())
                .build();
    }


    public ClientResponse fromClient(Client client) {
        if (client == null) {
            return null;
        }
        return new ClientResponse(
                client.getId(),
                client.getAgentId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getAddress(),
                client.getCin(),
                client.getBirthDate(),
                client.getPhoneNumber(),
                client.getPassword(),
                client.getIsFirstLogin(),
                client.getCreatedDate(),
                client.getCommercialRn(),
                client.getPatentNumber(),
                client.getIsPaymentAccountActivated(),
                client.getTypeHissab(),
                client.getCurrency()
        );
    }

}
