package com.example.transactionservice.transaction.entity;

public enum Role {
    ADMIN,
    AGENT,
    CLIENT;

    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

