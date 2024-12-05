package org.example.user.agent.service;


import lombok.RequiredArgsConstructor;
import org.example.user.agent.repository.ServiceRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    // pour que l'agent puis ajouter ses services dans l'espace agent

}
