package com.example.transactionservice.kafka;

public record TransactionConfirmation(
        // ici il faut envoyer les infos du transaction + les données du client
        // les attributs citer ici doivent etre les meme pour TransactionConfirmation fait dans le service Notification
) {
}
