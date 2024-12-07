package com.example.servicestiers.CMI.response;

public class TransferResponse {
    private String message;
    private Double transferredAmount;

    public TransferResponse(String message, Double transferredAmount) {
        this.message = message;
        this.transferredAmount = transferredAmount;
    }
}
