package org.example.cartevirtuelle.controller;

import org.example.cartevirtuelle.entity.VirtualCard;
import org.example.cartevirtuelle.service.VirtualCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/virtual-cards")
public class VirtualCardController {

    @Autowired
    private VirtualCardService virtualCardService;

    // Créer une carte virtuelle
    @PostMapping("/create/{userId}")
    public VirtualCard createCard(@PathVariable Long userId) {
        return virtualCardService.createVirtualCard(userId);
    }

    // Activer une carte virtuelle
    @PatchMapping("/activate/{cardId}")
    public VirtualCard activateCard(@PathVariable Long cardId) {
        return virtualCardService.activateCard(cardId);
    }

    // Désactiver une carte virtuelle
    @PatchMapping("/deactivate/{cardId}")
    public VirtualCard deactivateCard(@PathVariable Long cardId) {
        return virtualCardService.deactivateCard(cardId);
    }

    // Obtenir toutes les cartes d'un utilisateur
    @GetMapping("/user/{userId}")
    public List<VirtualCard> getCardsByUser(@PathVariable Long userId) {
        return virtualCardService.getCardsByUserId(userId);
    }
}
