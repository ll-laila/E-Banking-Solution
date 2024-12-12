package com.example.walletcryptoservice.walletClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "wallet-service",
        url = "${application.config.wallet-url}"
)
public interface WalletClient {

    @PostMapping("/addWallet")
    public ResponseEntity<String> saveWallet(@RequestBody Wallet wallet);

    @GetMapping("/getwallet/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable("walletId") String walletId);


    @GetMapping("/IdClient/{clientId}")
    public ResponseEntity<Wallet> getWalletByIdClient(@PathVariable("clientId") String clientId);


    @PutMapping("/updateWallet")
    public ResponseEntity<Wallet> updateWallet(@RequestBody Wallet walletRequest);


}
