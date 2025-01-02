package com.example.user.users.mapper;

import com.example.user.users.entity.Admin;
import com.example.user.users.request.AdminRequest;
import org.springframework.stereotype.Service;


@Service
public class AdminMapper {


    public static Admin toAdmin(AdminRequest request) {
        if (request == null) {
            return null;
        }
        return Admin.builder()
                .id(String.valueOf(request.id()))
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(request.password())
                .build();
    }

}
