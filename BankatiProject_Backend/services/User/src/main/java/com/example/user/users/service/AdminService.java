package com.example.user.users.service;


import com.example.user.users.entity.Admin;
import com.example.user.users.entity.Agent;
import com.example.user.users.mapper.AdminMapper;
import com.example.user.users.mapper.AgentMapper;
import com.example.user.users.repository.AdminRepository;
import com.example.user.users.repository.AgentRepository;
import com.example.user.users.request.AdminRequest;
import com.example.user.users.request.AgentRequest;
import com.example.user.users.response.AgentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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



    public AgentResponse findAgentById(String id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return agentMapper.fromAgent(agent);
    }


    public AgentResponse updateAgent(AgentRequest updateRequest) {

        return null;
    }


    public void delete(String id) {
        agentRepository.deleteById(id);
    }

    public List<AgentResponse> findAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(agentMapper :: fromAgent)
                .collect(Collectors.toList());
    }


}
