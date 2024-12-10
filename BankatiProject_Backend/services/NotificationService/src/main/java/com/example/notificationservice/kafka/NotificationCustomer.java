package com.example.notificationservice.kafka;

import com.example.notificationservice.notifications.Notification;
import com.example.notificationservice.notifications.NotificationRepository;
import com.example.notificationservice.twilio.Const;
import com.example.notificationservice.twilio.ENVConfig;
import com.example.notificationservice.twilio.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;



@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationCustomer {

    private final NotificationRepository notificationRepository;
    private final TwilioConfiguration twilioConfiguration;
    private final ENVConfig envConfig;


    @KafkaListener(topics = "transaction-topic")
    public void consumeOrderConfirmationNotifications(TransactionConfirmation transactionConfirmation)  {

        //save notification
        Notification notification = new Notification(null,LocalDateTime.now(),transactionConfirmation);
        notificationRepository.save(notification);

        //send sms
        String formattedPhoneNumber=formatPhoneNumber(transactionConfirmation.userPhone());
        //add type trans
        String message = "Bonjour " +transactionConfirmation.userFirstname()+" "+transactionConfirmation.userLastname() +
                "nous vous confirmons que votre transaction d'un montant de "+ transactionConfirmation.amount()+
           " "+transactionConfirmation.currency()+" a été "+ transactionConfirmation.transactionStatus();
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",message) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

    }


    public Object sendSms(String phoneNumber, String message) {
        if (phoneNumber!=null) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(envConfig.getTwilioSmsFromNo());
            return Message.creator(to,from,message).create();
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + phoneNumber + "] is not a valid number"
            );
        }
    }


    public String formatPhoneNumber(String phoneNumber) {
        String formatted = phoneNumber.substring(1);
        return "+212" + formatted;
    }

}
