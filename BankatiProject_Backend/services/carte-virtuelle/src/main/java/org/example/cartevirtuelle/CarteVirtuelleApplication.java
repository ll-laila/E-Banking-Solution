package org.example.cartevirtuelle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  // Active Feign dans l'application
public class CarteVirtuelleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarteVirtuelleApplication.class, args);
    }

}
