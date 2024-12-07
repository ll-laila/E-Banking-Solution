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
                .agent(request.agent())
                .build();

    }

    public ServiceResponse fromService(com.example.user.users.entity.Service service) {

        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getType(),
                service.getAgent()
        );


    }

}
