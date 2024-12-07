package com.example.servicestiers.CMI.mapper;

import com.example.servicestiers.CMI.entity.CMIEntity;
import com.example.servicestiers.CMI.request.TransferRequest;

import java.time.LocalDateTime;

public class CMIMapper {
    public static CMIEntity mapToEntity(TransferRequest request) {
        CMIEntity entity = new CMIEntity();
        entity.setSenderId(request.senderId());
        entity.setReceiverId(request.receiverId());
        entity.setAmount(request.amount());
        entity.setTransactionDate(LocalDateTime.now());
        return entity;
    }
}
