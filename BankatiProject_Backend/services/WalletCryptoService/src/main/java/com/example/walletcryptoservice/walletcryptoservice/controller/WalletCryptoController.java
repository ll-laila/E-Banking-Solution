package com.example.walletcryptoservice.walletcryptoservice.controller;

import com.example.walletcryptoservice.walletcryptoservice.request.WalletCryptoRequest;
import com.example.walletcryptoservice.walletcryptoservice.response.TransactionResponse;
import com.example.walletcryptoservice.walletcryptoservice.response.WalletCryptoResponse;
import com.example.walletcryptoservice.walletcryptoservice.service.CryptoService;
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

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("/addWalletCrypro")
    public ResponseEntity<String> saveWalletCrypto(@RequestBody WalletCryptoRequest wallet){
        return ResponseEntity.ok(walletService.saveWalletCrypto(wallet));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<WalletCryptoResponse> getWalletByUserId(@PathVariable String userId) {
        WalletCryptoResponse response = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/buy")
    public String buyCrypto(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount) {
        try {
            return walletService.buyCrypto(userId, cryptoName, amount);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    @PostMapping("/sell")
    public String sellCrypto(@RequestParam String userId, @RequestParam String cryptoName, @RequestParam double amount) {
        try {
            return walletService.sellCrypto(userId, cryptoName, amount);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    @GetMapping("/allTransaction/{idUser}")
    public ResponseEntity<List<TransactionResponse>> getUserTransaction(@PathVariable("idUser") String idUser) {
        return ResponseEntity.ok(walletService.findAllUserTransactions(idUser));
    }



    @PostMapping("/transferCryptoToMoney")
    public String transferCryptoToMoney(
            @RequestParam String userId,
            @RequestParam String cryptoName,
            @RequestParam double amount) {
        try {
            walletService.transferCryptoToClassicalMoney(userId,cryptoName,amount);
            return "Opération réussite";
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    @GetMapping("/getPriceCrypto/{cryptoName}")
    public double getPriceCrypto(@PathVariable("cryptoName") String cryptoName) {
        return cryptoService.getCryptoPrice(cryptoName);
    }



}
