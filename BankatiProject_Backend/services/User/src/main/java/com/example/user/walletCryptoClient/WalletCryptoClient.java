package com.example.user.walletCryptoClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@FeignClient(
        name = "walletcrypto-service",
        url = "${application.config.walletcrypto-url}"
)
public interface WalletCryptoClient {

    @PostMapping("/addWalletCrypro")
    public ResponseEntity<String> saveWalletCrypto(@RequestBody WalletCryptoRequest wallet);

    @GetMapping("/{userId}")
    public ResponseEntity<WalletCryptoResponse> getWalletByUserId(@PathVariable String userId);


    @PostMapping("/buy")
    public String buyCrypto(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount);


    @PostMapping("/sell")
    public String sellCrypto(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount);


    @GetMapping("/allTransaction/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser);


    @PostMapping("/transferCryptoToMoney")
    public String transferCryptoToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount);



    @GetMapping("/getPriceCrypto/{cryptoName}")
    public double getPriceCrypto(@PathVariable("cryptoName") String cryptoName);


}
