package com.example.user.users.mapper;

import com.example.user.users.entity.AgentServiceRequest;

public class AgentServiceMapper {

    static public AgentServiceRequest ConvertToDto(AgentServiceRequest agentService){
        return AgentServiceRequest.builder()
                .id(agentService.getId())
                .name(agentService.getName())
                .type(agentService.getType())
                .build();
    }


}
