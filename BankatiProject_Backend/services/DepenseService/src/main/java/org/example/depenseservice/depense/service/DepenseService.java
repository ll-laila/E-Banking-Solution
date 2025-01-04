package org.example.depenseservice.depense.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.depenseservice.depense.entity.Depense;
import org.example.depenseservice.depense.repository.DepenseRepository;
import org.example.depenseservice.kafka.TransactionConfirmation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DepenseService {

    private final DepenseRepository depenseRepository;

    public DepenseService(DepenseRepository depenseRepository) {
        this.depenseRepository = depenseRepository;
    }

    /**
     * Traitement d'une transaction pour mettre à jour les dépenses.
     */
    @Transactional
    public void processTransaction(TransactionConfirmation confirmation) {
        log.info("Traitement de la transaction ID : {}", confirmation.transactionId());

        if ("EXPENSE".equalsIgnoreCase(confirmation.transactionType().name())) {
            Optional<Depense> existingDepense = depenseRepository.findByUserPhone(confirmation.userPhone());

            if (existingDepense.isPresent()) {
                Depense depense = existingDepense.get();

                // Convertir BigDecimal en double
                double montantTransaction = confirmation.amount().doubleValue();
                double montantRestant = depense.getMontantRestant() - montantTransaction;

                if (montantRestant < 0) {
                    log.warn("Dépassement du budget pour l'utilisateur : {} {}",
                            confirmation.userFirstname(), confirmation.userLastname());
                }

                depense.setMontantRestant(Math.max(0, montantRestant));
                depenseRepository.save(depense);
                log.info("Mise à jour de la dépense terminée pour l'utilisateur : {} {}",
                        confirmation.userFirstname(), confirmation.userLastname());
            } else {
                log.warn("Aucune dépense définie pour l'utilisateur : {} {}",
                        confirmation.userFirstname(), confirmation.userLastname());
            }
        }
    }

    /**
     * Crée une nouvelle dépense pour un utilisateur.
     */
    public Depense createDepense(String userId, String userPhone, Double montant) {
        Depense depense = Depense.builder()
                .userId(userId)
                .userPhone(userPhone) // Ajout du numéro de téléphone
                .montant(montant)
                .montantRestant(montant)
                .dateCreation(LocalDate.now())
                .build();

        log.info("Création d'une nouvelle dépense pour l'utilisateur ID : {}, téléphone : {}", userId, userPhone);
        return depenseRepository.save(depense);
    }


    /**
     * Met à jour une dépense existante.
     */
    public Depense updateDepense(String depenseId, Double nouveauMontant) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense introuvable pour l'ID : " + depenseId));

        double difference = nouveauMontant - depense.getMontant();
        depense.setMontant(nouveauMontant);
        depense.setMontantRestant(depense.getMontantRestant() + difference);

        log.info("Mise à jour de la dépense ID : {}", depenseId);
        return depenseRepository.save(depense);
    }

    /**
     * Annule une dépense en réinitialisant son montant.
     */
    public Depense cancelDepense(String depenseId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense introuvable pour l'ID : " + depenseId));

        depense.setMontantRestant(0.0);
        log.info("Annulation de la dépense ID : {}", depenseId);
        return depenseRepository.save(depense);
    }

    /**
     * Récupère une dépense par son ID.
     */
    public Depense getDepenseById(String depenseId) {
        return depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Dépense introuvable pour l'ID : " + depenseId));
    }

    /**
     * Récupère toutes les dépenses pour un utilisateur spécifique.
     */
    public List<Depense> getAllDepensesByUser(String userId) {
        log.info("Récupération des dépenses pour l'utilisateur ID : {}", userId);
        return depenseRepository.findByUserId(userId);
    }
}
