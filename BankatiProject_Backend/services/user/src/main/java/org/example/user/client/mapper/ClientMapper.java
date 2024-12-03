package org.example.user.client.mapper;

import org.example.user.client.dto.ClientRequest;
import org.example.user.client.dto.ClientResponse;
import org.example.user.client.entity.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {


    public Client toClient(ClientRequest request) {
        if (request == null) {
            return null;
        }
        return Client.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .address(request.address())
                .CIN(request.CIN())
                .build();
    }

    public ClientResponse fromClient(Client client) {

        return new ClientResponse(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPassword(),
                client.getAddress(),
                client.getCIN(),
                client.getPortefeuilleId()
        );
    }


}
