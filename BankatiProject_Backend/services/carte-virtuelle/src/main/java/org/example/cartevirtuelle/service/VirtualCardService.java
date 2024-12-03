package org.example.cartevirtuelle.service;

import org.example.cartevirtuelle.entity.VirtualCard;
import org.example.cartevirtuelle.repository.VirtualCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VirtualCardService {

    @Autowired
    private VirtualCardRepository virtualCardRepository;

    // Créer une carte virtuelle
    public VirtualCard createVirtualCard(Long userId) {
        VirtualCard card = new VirtualCard();
        card.setCardNumber(generateCardNumber());
        card.setUserId(userId);
        card.setExpirationDate(LocalDateTime.now().plusYears(2)); // Expiration dans 2 ans
        card.setStatus("ACTIVE"); // La carte est activée par défaut
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());

        return virtualCardRepository.save(card);
    }

    // Générer un numéro de carte unique
    private String generateCardNumber() {
        return "VC" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    // Activer une carte virtuelle
    public VirtualCard activateCard(Long cardId) {
        VirtualCard card = virtualCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setStatus("ACTIVE");
        card.setUpdatedAt(LocalDateTime.now());
        return virtualCardRepository.save(card);
    }

    // Désactiver une carte virtuelle
    public VirtualCard deactivateCard(Long cardId) {
        VirtualCard card = virtualCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setStatus("INACTIVE");
        card.setUpdatedAt(LocalDateTime.now());
        return virtualCardRepository.save(card);
    }

    // Obtenir toutes les cartes d'un utilisateur
    public List<VirtualCard> getCardsByUserId(Long userId) {
        return virtualCardRepository.findByUserId(userId);
    }
}
