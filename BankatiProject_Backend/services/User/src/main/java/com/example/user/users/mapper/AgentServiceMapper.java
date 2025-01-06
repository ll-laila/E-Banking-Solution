package com.example.user.users.mapper;

import com.example.user.users.entity.AgentServiceRequest;
import com.example.user.users.entity.Service;

public class AgentServiceMapper {

    static public Service ConvertToDto(Service agentService){
        return Service.builder()
                .id(agentService.getId())
                .name(agentService.getName())
                .type(agentService.getType())
                .build();
    }


}
