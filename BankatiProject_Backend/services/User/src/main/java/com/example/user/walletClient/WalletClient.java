package com.example.user.walletClient;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "wallet-service",
        url = "${application.config.wallet-url}"
)
public interface WalletClient {
}
