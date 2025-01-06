package com.example.virtualcard.virtualcard.controller;

import com.example.virtualcard.virtualcard.entity.VirtualCard;
import com.example.virtualcard.virtualcard.service.VirtualCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/virtualcard")
@RequiredArgsConstructor
public class VirtualCardController {
    @Autowired
    private VirtualCardService virtualCardService;

    @PostMapping("/virtualcard/create/{userId}")
    public VirtualCard createCard(@PathVariable String userId) {
        return virtualCardService.createVirtualCard(userId);
    }

    @PatchMapping("/activate/{cardId}")
    public VirtualCard activateCard(@PathVariable String cardId) {
        return virtualCardService.activateCard(cardId);
    }

    @PatchMapping("/deactivate/{cardId}")
    public VirtualCard deactivateCard(@PathVariable String cardId) {
        return virtualCardService.deactivateCard(cardId);
    }

    @GetMapping("/user/{userId}")
    public VirtualCard getCardsByUser(@PathVariable String userId) {
        return virtualCardService.getCardsByUserId(userId);
    }


    @PostMapping("/feedCard")
    public VirtualCard feedCard(@RequestParam String clientId, @RequestParam double somme){
        return virtualCardService.feedCard(clientId,somme);
    }
}
