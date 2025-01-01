package com.example.user.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class ErrorDto {

    private String message;
    private String details;
    private String path;
    //private LocalDateTime timestamp;
}
