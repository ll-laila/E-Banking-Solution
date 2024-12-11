package com.example.user.serviceTiersClient;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "servicetiers-service",
        url = "${application.config.servicestiers-url}"
)
public interface ServiceTiersClient {

    @GetMapping("/feedWallet")
    public ResponseEntity<Boolean> feedWallet(@RequestParam String clientId, @RequestParam double amount);
}
