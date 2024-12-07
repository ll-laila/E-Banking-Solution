package com.example.servicestiers.CMI.request;

public record TransferRequest(Long senderId, Long receiverId, Double amount) {}
