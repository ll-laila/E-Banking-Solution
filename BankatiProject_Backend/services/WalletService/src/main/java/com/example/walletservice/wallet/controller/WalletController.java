package com.example.walletservice.wallet.controller;

import com.example.walletservice.wallet.request.BankAccountRequest;
import com.example.walletservice.wallet.request.WalletRequest;
import com.example.walletservice.wallet.response.BankAccountResponse;
import com.example.walletservice.wallet.response.WalletResponse;
import com.example.walletservice.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> saveWallet(@RequestBody WalletRequest wallet){
        return ResponseEntity.ok(walletService.saveWallet(wallet));
    }

    @GetMapping("/getwallet/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable("walletId") String walletId){
        return ResponseEntity.ok(walletService.findWallet(walletId));
    }

    @GetMapping("/getBankAccount/{bankAccountId}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable("bankAccountId") String bankAccountId){
        return ResponseEntity.ok(walletService.findBankAccountById(bankAccountId));
    }

    @PutMapping("/updateBankAccount")
    public String updateBankAccount(@RequestBody BankAccountRequest bankAccountRequest){
        return walletService.updateBankAccount(bankAccountRequest);
    }

    @GetMapping("/IdClient/{clientId}")
    public ResponseEntity<WalletResponse> getWalletByIdClient(@PathVariable("clientId") String clientId){
        return ResponseEntity.ok(walletService.findWalletByIdClient(clientId));
    }


    @PutMapping("/updateWallet")
    public ResponseEntity<WalletResponse> updateWallet(@RequestBody WalletRequest walletRequest){
        return ResponseEntity.ok(walletService.updateWallet(walletRequest));
    }




}
