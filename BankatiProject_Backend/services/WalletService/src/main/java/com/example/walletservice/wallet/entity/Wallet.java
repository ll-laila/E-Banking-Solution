package com.example.walletservice.wallet.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Wallet {
    @Id
    private String id;
    private Double balance;
    private String clientId;
    private String bankAccountId;

  //  @DBRef
  //  private BankAccount bankAccount;
}
