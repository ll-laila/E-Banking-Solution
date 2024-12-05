package org.example.user.admin.service;

import org.example.user.admin.Mapper.AdminMapper;
import org.example.user.admin.dto.AdminRequest;
import org.example.user.admin.entity.Admin;
import org.example.user.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    //@Autowired
   // private AgentRepository agentRepository;


    //--------------------------------------Admin-----------------------------------//

    public Admin saveAdmin(AdminRequest request) {
        return adminRepository.save(adminMapper.toAdmin(request));
    }

    //--------------------------------------Agent-----------------------------------//

/*
    public String registerAgent(AgentRequest request) {
        return null;
    }


    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }


    public Agent findAgentById(Long id) {
        return agentRepository.findById(id);
    }


    public String updateAgent(Long id , AgentRequest updatedAgent) {
        return null;
    }

*/




}
