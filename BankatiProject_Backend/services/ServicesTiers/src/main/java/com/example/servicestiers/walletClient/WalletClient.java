package com.example.servicestiers.walletClient;


import com.example.servicestiers.CMI.request.WalletTransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "wallet-service",
        url = "${application.config.wallet-url}"
)
public interface WalletClient {


    @PostMapping("/addWallet")
    public ResponseEntity<String> saveWallet(@RequestBody WalletRequest walletRequest);

    @GetMapping("/getwallet/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable("walletId") Long walletId);

    @PostMapping("/debit-wallet")
    public void debitWallet(@RequestBody WalletTransactionRequest request);

    @PostMapping("/credit-wallet")
    public void creditWallet(@RequestBody WalletTransactionRequest request);
}
