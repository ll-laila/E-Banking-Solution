package org.example.user.portefeuille;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PorteFeuilleRepository  extends JpaRepository<Portefeuille, String> {
    Optional<Portefeuille> findByClientId(String clientId);
    Optional<Portefeuille> findByClientCin(String clientCin);
}



