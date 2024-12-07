package com.example.servicestiers.servicestiers.controller;

import com.example.servicestiers.CMI.request.BalanceCheckRequest;
import com.example.servicestiers.CMI.request.TransferRequest;
import com.example.servicestiers.CMI.response.TransferResponse;
import com.example.servicestiers.CMI.service.CMIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/servicestiers")
@RequiredArgsConstructor
public class ServicesTiersController {
    @Autowired
    private CMIService cmiService;

    @PostMapping("/check-balance")
    public ResponseEntity<Boolean> checkBalance(@RequestBody BalanceCheckRequest request) {
        boolean hasEnoughBalance = cmiService.checkBalance(request.userId(), request.amount());
        return ResponseEntity.ok(hasEnoughBalance);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferAmount(@RequestBody TransferRequest request) {
        TransferResponse response = cmiService.transferAmount(request);
        return ResponseEntity.ok(response);
    }
}