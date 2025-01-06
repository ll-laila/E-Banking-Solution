package org.example.depenseservice.depense.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.depenseservice.depense.entity.Depense;
import org.example.depenseservice.depense.service.DepenseService;
import org.example.depenseservice.kafka.TransactionConfirmation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/depenses")
public class DepenseController {

    private final DepenseService depenseService;

    public DepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    /**
     * Endpoint pour traiter une transaction.
     */
    @PostMapping("/process")
    public ResponseEntity<String> processTransaction(@RequestBody TransactionConfirmation confirmation) {
        depenseService.processTransaction(confirmation);
        return ResponseEntity.ok("Transaction processed successfully.");
    }

    /**
     * Endpoint pour créer une nouvelle dépense.
     */
   @PostMapping("/create")
    public ResponseEntity<Depense> createDepense(@RequestParam String userId, @RequestParam String userPhone, @RequestParam Double montant) {
        Depense depense = depenseService.createDepense(userId, userPhone, montant);
        return ResponseEntity.ok(depense);
    }

    /**
     * Endpoint pour mettre à jour une dépense existante.
     */
    @PutMapping("/update/{depenseId}")
    public ResponseEntity<Depense> updateDepense(@PathVariable String depenseId, @RequestParam Double nouveauMontant) {
        Depense updatedDepense = depenseService.updateDepense(depenseId, nouveauMontant);
        return ResponseEntity.ok(updatedDepense);
    }

    /**
     * Endpoint pour annuler une dépense.
     */
    @DeleteMapping("/cancel/{depenseId}")
    public ResponseEntity<Depense> cancelDepense(@PathVariable String depenseId) {
        Depense canceledDepense = depenseService.cancelDepense(depenseId);
        return ResponseEntity.ok(canceledDepense);
    }

    /**
     * Endpoint pour récupérer une dépense par son ID.
     */
    @GetMapping("/get/{depenseId}")
    public ResponseEntity<Depense> getDepenseById(@PathVariable String depenseId) {
        Depense depense = depenseService.getDepenseById(depenseId);
        return ResponseEntity.ok(depense);
    }

    /**
     * Endpoint pour récupérer toutes les dépenses d'un utilisateur par son numéro de téléphone.
     */
    @GetMapping("/list")
    public ResponseEntity<List<Depense>> getAllDepensesByUser(@RequestParam String userId) {
        List<Depense> depenses = depenseService.getAllDepensesByUser(userId);
        return ResponseEntity.ok(depenses);
    }
}
