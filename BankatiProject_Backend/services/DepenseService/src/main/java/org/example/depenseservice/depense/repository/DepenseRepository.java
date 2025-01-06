package org.example.depenseservice.depense.repository;

import org.example.depenseservice.depense.entity.Depense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DepenseRepository extends MongoRepository<Depense, String> {
    List<Depense> findByUserId(String userId);
    Optional<Depense> findByUserPhone(String userPhone); // Mise à jour ici
    Optional<Depense> findByUserIdAndId(String userId, String depenseId);
}
