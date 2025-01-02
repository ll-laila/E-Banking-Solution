package com.example.user.users.repository;

import com.example.user.users.entity.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    Optional<Agent> findByPhoneNumber(String phoneNumber);
}
