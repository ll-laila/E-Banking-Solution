package com.example.user.depenseClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "depense-service",
        url = "${application.config.depense-url}"
)
public interface DepenseClient {

    /**
     * Endpoint pour créer une nouvelle dépense.
     */
    @PostMapping("/create")
    public ResponseEntity<DepenseResponse> createDepense(@RequestParam String userId, @RequestParam String userPhone, @RequestParam Double montant);


    /**
     * Endpoint pour mettre à jour une dépense existante.
     */
    @PutMapping("/update/{depenseId}")
    ResponseEntity<DepenseResponse> updateDepense(@PathVariable String depenseId,
                                                  @RequestParam Double nouveauMontant);

    /**
     * Endpoint pour annuler une dépense.
     */
    @DeleteMapping("/cancel/{depenseId}")
    ResponseEntity<DepenseResponse> cancelDepense(@PathVariable String depenseId);

    /**
     * Endpoint pour récupérer une dépense par son ID.
     */

    @GetMapping("/get/{depenseId}")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable String depenseId);

    /**
     * Endpoint pour récupérer toutes les dépenses d'un utilisateur par son ID.
     */
    @GetMapping("/depenses/list")
    ResponseEntity<List<DepenseResponse>> getAllDepensesByUser(@RequestParam String userId);
}
