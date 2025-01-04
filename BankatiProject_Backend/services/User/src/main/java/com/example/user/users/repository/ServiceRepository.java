package com.example.user.users.repository;


import com.example.user.users.entity.AgentServiceRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ServiceRepository extends MongoRepository<AgentServiceRequest, String> {
    Optional<AgentServiceRequest> findServiceById(String id);
    List<AgentServiceRequest> findAllByAgentId(String id);
}
