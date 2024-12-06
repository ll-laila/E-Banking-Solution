package com.example.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationCustomer {

    @KafkaListener(topics = "transaction-topic")
    public void consumeOrderConfirmationNotifications(TransactionConfirmation transactionConfirmation)  {

        // save la notification dans la base de données ici avec NotificationRepository

       // ici send sms  (ou email si on arrive pas de le faire avec sms)
    }


}
