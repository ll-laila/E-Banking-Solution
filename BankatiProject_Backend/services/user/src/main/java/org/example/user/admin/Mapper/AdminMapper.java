package org.example.user.admin.Mapper;


import org.example.user.admin.dto.AdminRequest;
import org.example.user.admin.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toAdmin(AdminRequest request) {
        if (request == null) {
            return null;
        }
        return Admin.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(request.password())
                .build();
    }


}
