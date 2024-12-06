package com.example.notificationservice.notifications;

import com.example.notificationservice.kafka.TransactionConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;
    private LocalDateTime notificationDate;
    private TransactionConfirmation transactionConfirmation;
}