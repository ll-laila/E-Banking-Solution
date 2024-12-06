package com.example.virtualcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class VirtualCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualCardApplication.class, args);
	}

}
