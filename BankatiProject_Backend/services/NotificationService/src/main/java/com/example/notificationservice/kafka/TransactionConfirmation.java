package com.example.notificationservice.kafka;

public record TransactionConfirmation(
        // ici il faut citer les infos du transaction + les données du client
        // les attributs siter ici doivent etre les meme pour TransactionConfirmation fait dans le service Transaction
) {
}
