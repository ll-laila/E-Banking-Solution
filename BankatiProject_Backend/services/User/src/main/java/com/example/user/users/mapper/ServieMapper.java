package com.example.user.users.mapper;

import com.example.user.users.request.ServiceRequest;
import com.example.user.users.response.ServiceResponse;
import org.springframework.stereotype.Service;

@Service
public class ServieMapper {

    public com.example.user.users.entity.Service toService(ServiceRequest request) {
        if (request == null) {
            return null;
        }
        return com.example.user.users.entity.Service.builder()
                .id(request.id())
                .name(request.name())
                .type(request.type())
                .agentId(request.agentId())
                .build();

    }



}
