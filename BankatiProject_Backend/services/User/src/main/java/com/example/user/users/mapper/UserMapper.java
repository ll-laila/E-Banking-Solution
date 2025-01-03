package com.example.user.users.mapper;


import com.example.user.users.dto.SignUpDto;
import com.example.user.users.dto.UserDto;
import com.example.user.users.entity.Admin;
import com.example.user.users.entity.Agent;
import com.example.user.users.entity.Client;
import com.example.user.users.entity.User;
import com.example.user.users.request.UserRequest;
import com.example.user.users.response.UserResponse;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Date;

@Service
public class UserMapper {

    // Converts UserRequest to a User entity
    public User toUser(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .id(request.id())
                .agentId(request.agentId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .cin(request.cin())
                .birthDate(request.birthDate())
                .phoneNumber(request.phoneNumber())
                .role(request.role()) // Use role provided in the request
                .password(request.password())
                .isFirstLogin(request.isFirstLogin())
                .commercialRn(request.commercialRn())
                .image(request.image())
                .patentNumber(request.patentNumber())
                .typeHissab(request.typeHissab())
                .currency(request.currency())
                .build();
    }

    // Converts a User entity to UserResponse DTO
    public UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }

          return new UserResponse(
                user.getId(),
                user.getAgentId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getCin(),
                user.getBirthDate(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getPassword(),
                user.isFirstLogin(),
                user.getCommercialRn(),
                user.getImage(),
                user.getPatentNumber(),
                user.getIsPaymentAccountActivated(),
                user.getTypeHissab(),
                user.getCurrency()
        );
    }

    // Converts a generic user object to UserDto
    public UserDto toUserDto(Object user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        if (user instanceof User u) {
            userDto.setId(u.getId());
            userDto.setAgentId(u.getAgentId());
            userDto.setFirstName(u.getFirstName());
            userDto.setLastName(u.getLastName());
            userDto.setEmail(u.getEmail());
            userDto.setAddress(u.getAddress());
            userDto.setCin(u.getCin());
            userDto.setBirthDate(u.getBirthDate());
            userDto.setPhoneNumber(u.getPhoneNumber());
            userDto.setRole(u.getRole());
            userDto.setPassword(u.getPassword());
            userDto.setFirstLogin(u.isFirstLogin());
            userDto.setCommercialRn(u.getCommercialRn());
            userDto.setImage(u.getImage());
            userDto.setPatentNumber(u.getPatentNumber());
            userDto.setIsPaymentAccountActivated(u.getIsPaymentAccountActivated());
            userDto.setTypeHissab(u.getTypeHissab());
            userDto.setCurrency(u.getCurrency());
        }

        return userDto;
    }

    // Converts SignUpDto to a User entity
    public User signUpToUser(SignUpDto request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .role(request.getRole()) // Map the role during sign-up
                .build();
    }
}
