package com.example.walletcryptoservice.walletcryptoservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import java.util.Map;

@Service
public class CryptoService {

    public double getCryptoPrice(String cryptoName) {
        String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptoName + "&vs_currencies=usd";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Map<String, Object>> response = restTemplate.exchange(API_URL,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Map<String, Object>>>() {}).getBody();

        if (response == null || !response.containsKey(cryptoName)) {
            throw new RuntimeException("Les données de la crypto-monnaie " + cryptoName + " ne sont pas disponibles.");
        }

        Map<String, Object> cryptoData = response.get(cryptoName);
        if (cryptoData == null) {
            throw new RuntimeException("Aucune donnée disponible pour " + cryptoName);
        }

        Object priceObject = cryptoData.get("usd");

        if (priceObject instanceof Number) {
            return ((Number) priceObject).doubleValue();
        } else {
            throw new RuntimeException("Le prix pour " + cryptoName + " n'est pas valide.");
        }
    }
}
