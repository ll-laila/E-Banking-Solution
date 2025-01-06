package org.example.depenseservice.depense.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
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
}

