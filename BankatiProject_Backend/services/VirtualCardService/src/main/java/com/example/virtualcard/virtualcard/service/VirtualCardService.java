package com.example.virtualcard.virtualcard.service;


import com.example.virtualcard.virtualcard.entity.VirtualCard;
import com.example.virtualcard.virtualcard.repository.VirtualCardRepository;
import com.example.virtualcard.walletClient.Wallet;
import com.example.virtualcard.walletClient.WalletClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VirtualCardService {

    @Autowired
    private VirtualCardRepository virtualCardRepository;

    @Autowired
    private WalletClient walletClient;


    // Créer une carte virtuelle
    public VirtualCard createVirtualCard(String userId) {
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
    public VirtualCard activateCard(String cardId) {
        VirtualCard card = virtualCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setStatus("ACTIVE");
        card.setUpdatedAt(LocalDateTime.now());
        return virtualCardRepository.save(card);
    }

    // Désactiver une carte virtuelle
    public VirtualCard deactivateCard(String cardId) {
        VirtualCard card = virtualCardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setStatus("INACTIVE");
        card.setUpdatedAt(LocalDateTime.now());
        return virtualCardRepository.save(card);
    }

    // Obtenir toutes les cartes d'un utilisateur
    public VirtualCard getCardsByUserId(String userId) {
        VirtualCard card = virtualCardRepository.findByUserId(userId);
        System.out.println("virtual card "+ card);
        return card;
    }


    public VirtualCard feedCard(String clientId, double somme){
        Wallet wallet = walletClient.getWalletByIdClient(clientId).getBody();
        VirtualCard virtualCard = getCardsByUserId(clientId);
        Wallet walletUpdate = new Wallet(wallet.id(),wallet.balance()-somme,wallet.clientId(),wallet.bankAccountId());
        walletClient.updateWallet(walletUpdate);
        VirtualCard virtualCardUpdate = new VirtualCard(virtualCard.getId(),
                virtualCard.getCardNumber(),
                virtualCard.getUserId(),
                virtualCard.getExpirationDate() ,
                virtualCard.getStatus(),
                virtualCard.getCreatedAt(),
                virtualCard.getUpdatedAt(),
                virtualCard.getMontant() + somme
                );
        return virtualCardRepository.save(virtualCardUpdate);
    }
}
