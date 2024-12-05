package org.example.user.admin.controller;


import lombok.RequiredArgsConstructor;
import org.example.user.admin.dto.AdminRequest;
import org.example.user.admin.entity.Admin;
import org.example.user.admin.service.AdminService;
import org.example.user.agent.dto.AgentRequest;
import org.example.user.agent.dto.AgentResponse;
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


    @PostMapping("/addAgent")
    public ResponseEntity<AgentResponse> AddAgent(@RequestBody AgentRequest request){
        return ResponseEntity.ok(service.addAgent(request));
    }



    @GetMapping("/getAgent/{id}")
    public ResponseEntity<AgentResponse> getAgentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findAgentById(id));
    }


    @PutMapping("/updateAgent/{id}")
    public ResponseEntity<AgentResponse> updateUser(@RequestBody AgentRequest updatedAgent){
        return ResponseEntity.ok(service.updateAgent(updatedAgent));
    }



    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id)  {
        service.delete(id);
    }


    @GetMapping("/listAgent")
    public ResponseEntity<List<AgentResponse>> getAllAgents() {
        return ResponseEntity.ok( service.findAllAgents());
    }



}
