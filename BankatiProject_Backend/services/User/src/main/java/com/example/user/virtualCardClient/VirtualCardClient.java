package com.example.user.virtualCardClient;

import com.example.user.walletClient.WalletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
        name = "virtualcard-service",
        url = "${application.config.virtualCard-url}"
)
public interface VirtualCardClient {
    @PostMapping("/virtualcard/create/{userId}")
    public VirtualCardResponse createCard(@PathVariable String userId);

    @PatchMapping("/activate/{cardId}")
    public VirtualCardResponse activateCard(@PathVariable String cardId);
    @PatchMapping("/deactivate/{cardId}")
    public VirtualCardResponse deactivateCard(@PathVariable String cardId);

    @GetMapping("/user/{userId}")
    public VirtualCardResponse getCardsByUser(@PathVariable String userId) ;


    @PostMapping("/feedCard")
    public VirtualCardResponse feedCard(@RequestParam String clientId, @RequestParam Double somme);

}
