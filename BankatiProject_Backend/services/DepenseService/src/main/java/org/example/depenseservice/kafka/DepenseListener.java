//package org.example.depenseservice.kafka;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.depenseservice.depense.service.DepenseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class DepenseListener {
//
//    @Autowired
//    private  DepenseService depenseService;
//
//    @KafkaListener(topics = "transaction-topic", groupId = "depense-service-group")
//    public void handleTransactionMessage(TransactionConfirmation confirmation) {
//       // log.info("Message reçu pour la transaction ID : {}, Type : {}, Montant : {}",
//              //  confirmation.transactionId(), confirmation.transactionType(), confirmation.amount());
//
//        // Validation basique
//        if (confirmation.userPhone() == null || confirmation.amount() == null) {
//           // log.error("Message invalide reçu : {}", confirmation);
//            return;
//        }
//
//        // Traitement des dépenses
//        try {
//            depenseService.processTransaction(confirmation);
//           // log.info("Transaction ID : {} traitée avec succès", confirmation.transactionId());
//        } catch (Exception e) {
//            //log.error("Erreur lors du traitement de la transaction ID : {}", confirmation.transactionId(), e);
//        }
//    }
//}
