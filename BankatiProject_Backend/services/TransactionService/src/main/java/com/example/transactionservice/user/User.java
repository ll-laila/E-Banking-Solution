package com.example.transactionservice.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record User(String id,
                   @NotNull(message = "Firstname is required")
                    String firstname,
                   @NotNull(message = "Lastname is required")
                    String lastname,
                   @NotNull(message = "phone is required")
                    String phone,
                   @NotNull(message = "role is required")
                   String role,
                   @NotNull(message = "Email is required")
                    @Email(message = "The customer email is not correctly formatted")
                    String email) {
}
