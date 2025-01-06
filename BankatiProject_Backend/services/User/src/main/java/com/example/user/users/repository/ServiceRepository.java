package com.example.user.users.repository;


import com.example.user.users.entity.AgentServiceRequest;
import com.example.user.users.entity.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {
    Optional<Service> findServiceById(String id);
    List<Service> findAllByAgentId(String id);
}
