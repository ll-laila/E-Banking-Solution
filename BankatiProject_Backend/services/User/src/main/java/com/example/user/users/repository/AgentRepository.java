package com.example.user.users.repository;

import com.example.user.users.entity.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {

}
