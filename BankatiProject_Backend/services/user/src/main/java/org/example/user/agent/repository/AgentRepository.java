package org.example.user.agent.repository;

import org.example.user.agent.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, String> {
}
