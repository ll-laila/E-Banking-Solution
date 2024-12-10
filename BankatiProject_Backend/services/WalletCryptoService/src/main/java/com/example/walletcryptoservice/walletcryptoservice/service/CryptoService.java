package com.example.walletcryptoservice.walletcryptoservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CryptoService {
    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&ripple&vs_currencies=usd";

    public double getCryptoPrice(String cryptoName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(API_URL, cryptoName);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> cryptoData = (Map<String, Object>) response.get(cryptoName);
        return (double) cryptoData.get("usd");
    }
}
