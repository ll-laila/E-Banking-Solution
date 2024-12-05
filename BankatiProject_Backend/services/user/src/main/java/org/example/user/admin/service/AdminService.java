package org.example.user.admin.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.user.admin.Mapper.AdminMapper;
import org.example.user.admin.dto.AdminRequest;
import org.example.user.admin.entity.Admin;
import org.example.user.admin.repository.AdminRepository;
import org.example.user.agent.Mapper.AgentMapper;
import org.example.user.agent.dto.AgentRequest;
import org.example.user.agent.dto.AgentResponse;
import org.example.user.agent.entity.Agent;
import org.example.user.agent.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;


@Service
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentMapper agentMapper;

    //--------------------------------------Admin-----------------------------------//

    public Admin saveAdmin(AdminRequest request) {
        return adminRepository.save(adminMapper.toAdmin(request));
    }

    //--------------------------------------Agent-----------------------------------//


    public AgentResponse addAgent(AgentRequest request) {
        return null;
    }



    public AgentResponse findAgentById(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return agentMapper.fromAgent(agent);
    }


    public AgentResponse updateAgent(AgentRequest updateRequest) {

        return null;
    }


    public void delete(Long id) {
        agentRepository.deleteById(id);
    }

    public List<AgentResponse> findAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(agentMapper :: fromAgent)
                .collect(Collectors.toList());
    }

}
