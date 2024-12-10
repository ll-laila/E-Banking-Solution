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


    @GetMapping("/{userId}")
    public ResponseEntity<WalletCryptoResponse> getWalletByUserId(@PathVariable String userId);


    @PostMapping("/buy")
    public ResponseEntity<String> buyCrypto(@RequestParam String userBuyId, @RequestParam String userSendId, @RequestParam String cryptoName, @RequestParam double amount);



    @PostMapping("/setToSell")
    public ResponseEntity<String> setCryptosToSell(@RequestParam String userId, @RequestBody Map<String, Double> cryptosToSell);



    @PostMapping("/transferCryptoToMoney")
    public ResponseEntity<String> transferCryptoToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount);




    @GetMapping("/allCryptosToSell")
    public ResponseEntity<Map<String, Map<String, Double>>> getCryptosToSell();



    @GetMapping("/allTransaction/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser);

}
