package com.example.user.depenseClient;

import java.time.LocalDate;

public record DepenseRequest(
        String userId,
        String userPhone,
        Double montant,
        LocalDate dateCreation
) {}

