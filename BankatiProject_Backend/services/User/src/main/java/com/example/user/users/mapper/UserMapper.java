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

@Service
public class UserMapper {

    // Converts UserRequest to a User entity
    public User toUser(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(request.password())
                .role(request.role()) // Use role provided in the request
                .build();
    }

    // Converts a User entity to UserResponse DTO
    public UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
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
            userDto.setEmail(u.getEmail());
            userDto.setFirstName(u.getFirstName());
            userDto.setLastName(u.getLastName());
            userDto.setPhoneNumber(u.getPhoneNumber());
            userDto.setRole(u.getRole());
            userDto.setPassword(u.getPassword());
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
