package com.example.user.users.response;


import com.example.user.users.entity.Agent;

public record ServiceResponse(
         String id,
         String name,

         String type,

         Agent agent
) {
}
