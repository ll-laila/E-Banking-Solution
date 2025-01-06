package com.example.user.depenseClient;

import java.time.LocalDate;

public record DepenseResponse(
        String id,
        String userId,
        String userPhone,
        Double montant,
        Double montantRestant,
        LocalDate dateCreation



) {}
