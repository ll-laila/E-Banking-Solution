package com.example.user.users.request;

import com.example.user.users.entity.Agent;
import com.example.user.users.response.AgentResponse;

public record ServiceRequest (
        String id,
        String name,

        String type,

        Agent agent
){
}
