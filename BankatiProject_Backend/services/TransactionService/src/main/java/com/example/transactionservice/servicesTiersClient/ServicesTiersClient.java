package com.example.transactionservice.servicesTiersClient;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "servicestiers-service",
        url = "${application.config.servicesTiers-url}"
)
public interface ServicesTiersClient {
}
