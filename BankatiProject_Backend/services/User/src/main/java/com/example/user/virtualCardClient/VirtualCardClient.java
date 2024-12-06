package com.example.user.virtualCardClient;

import org.springframework.cloud.openfeign.FeignClient;



@FeignClient(
        name = "virtualcard-service",
        url = "${application.config.virtualCard-url}"
)
public interface VirtualCardClient {

}
