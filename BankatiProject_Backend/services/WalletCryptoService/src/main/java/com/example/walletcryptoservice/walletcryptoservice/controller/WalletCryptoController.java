package com.example.walletcryptoservice.walletcryptoservice.controller;

import com.example.walletcryptoservice.walletcryptoservice.response.TransactionResponse;
import com.example.walletcryptoservice.walletcryptoservice.response.WalletCryptoResponse;
import com.example.walletcryptoservice.walletcryptoservice.service.CryptoWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/walletcrypto")
public class WalletCryptoController {

    @Autowired
    private CryptoWalletService walletService;


    @GetMapping("/{userId}")
    public ResponseEntity<WalletCryptoResponse> getWalletByUserId(@PathVariable String userId) {
        WalletCryptoResponse response = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/buy")
    public ResponseEntity<String> buyCrypto(@RequestParam String userBuyId,@RequestParam String userSendId, @RequestParam String cryptoName, @RequestParam double amount) {
        walletService.buyCrypto(userBuyId,userSendId, cryptoName, amount);
        return ResponseEntity.ok("Crypto achetée avec succès.");
    }


    @PostMapping("/setToSell")
    public ResponseEntity<String> setCryptosToSell(@RequestParam String userId, @RequestBody Map<String, Double> cryptosToSell) {
        walletService.setCryptosToSell(userId, cryptosToSell);
        return ResponseEntity.ok("Les cryptos à vendre ont été mises à jour avec succès.");
    }



    @PostMapping("/transferCryptoToMoney")
    public ResponseEntity<String> transferCryptoToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount) {
        try {
            walletService.transferCryptoToClassicalMoney(userId,cryptoName,amount);
            return ResponseEntity.ok("Opération réussite");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    @GetMapping("/allCryptosToSell")
    public ResponseEntity<Map<String, Map<String, Double>>> getCryptosToSell() {
        Map<String, Map<String, Double>> cryptosToSell = walletService.getCryptosToSell();
        return ResponseEntity.ok(cryptosToSell);
    }




    @GetMapping("/allTransaction/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser) {
        return ResponseEntity.ok(walletService.findAllUserTransactions(idUser));
    }




}
