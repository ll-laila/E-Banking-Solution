package org.example.user.wallet;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="", url = "${}")
public interface WalletClient {

    @PostMapping("/addWallet")
    public ResponseEntity<Long> saveWallet(@RequestBody Wallet wallet);

    @GetMapping("/getwallet/{clientId}")
    public ResponseEntity<Long> getWallet(@RequestBody Long walletId);


    @GetMapping("/client/{clientId}")
    public ResponseEntity<Double> findWalletBalance(@PathVariable("clientId") Long userId);
}
