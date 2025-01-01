package com.example.user.users.request;

public record PasswordChangeRequest(
        String phoneNumber,
        String oldPassword,
        String newPassword)
{}
