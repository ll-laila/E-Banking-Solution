package org.example.user.admin.controller;


import lombok.RequiredArgsConstructor;
import org.example.user.admin.dto.AdminRequest;
import org.example.user.admin.entity.Admin;
import org.example.user.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService service;

    //--------------------------------------Admin-----------------------------------//

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody AdminRequest request) {
        return ResponseEntity.ok(service.saveAdmin(request));
    }

    //--------------------------------------Agent-----------------------------------//

/*
    @GetMapping("/listAgent")
    public List<Agent> getAllAgents() {
        return service.findAgents();
    }


    @GetMapping("/agent/{id}")
    public Agent getAgentById(@PathVariable("id") Long id) {
        return service.findAgentById(id);
    }


    @PostMapping("/addAgent")
    public ResponseEntity<AgentResponse> registerAgent(@RequestBody AgentRequest request) {
        return ResponseEntity.ok(service.registerAgent(request));
    }


    @PutMapping("/updateAgent/{id}")
    public ResponseEntity<AgentResponse> updateUser(@PathVariable("id") Long id, @RequestBody AgentRequest updatedAgent)  {
        return ResponseEntity.ok(service.updateAgent(id,updatedAgent));
    }



    @DeleteMapping("/delete/{id}")
    public AgentResponse deleteUser(@PathVariable("id") Long id)  {
        return service.deleteAgent(id);
    }


*/


}
