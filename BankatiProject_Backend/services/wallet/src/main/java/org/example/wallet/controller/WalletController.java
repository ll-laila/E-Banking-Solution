package org.example.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.example.wallet.dto.WalletRequest;
import org.example.wallet.dto.WalletResponse;
import org.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    @Autowired
    private WalletService walletService;


    @PostMapping("/addWallet")
    public ResponseEntity<Long> saveWallet(@RequestBody WalletRequest wallet){
        return ResponseEntity.ok(walletService.saveWallet(wallet));
    }

    @GetMapping("/getwallet/{clientId}")
    public ResponseEntity<WalletResponse> getWallet(@RequestBody Long walletId){
        return ResponseEntity.ok(walletService.findWallet(walletId));
    }



    @GetMapping("/client/{clientId}")
    public ResponseEntity<Double> findWalletBalance(
            @PathVariable("clientId") Long userId)
    {
        return ResponseEntity.ok(walletService.getWalletBalance(userId));
    }





}
