package org.example.depenseservice.depense.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Depense {
    @Id
    private String id;
    private String userId;        // Identifiant de l'utilisateur
    private String userPhone;
    private Double montant;       // Montant défini par l'utilisateur comme budget
    private Double montantRestant; // Montant restant après chaque transaction
    private LocalDate dateCreation; // Date de création du budget

    public Depense(String id, String userId, String userPhone, Double montant, Double montantRestant, LocalDate dateCreation) {
        this.id = id;
        this.userId = userId;
        this.userPhone = userPhone;
        this.montant = montant;
        this.montantRestant = montantRestant;
        this.dateCreation = dateCreation;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(Double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
}

