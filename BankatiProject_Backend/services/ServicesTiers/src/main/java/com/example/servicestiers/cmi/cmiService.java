package com.example.servicestiers.cmi;

import com.example.servicestiers.walletClient.WalletClient;
import com.example.servicestiers.walletClient.WalletRequest;
import com.example.servicestiers.walletClient.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;



@RestController
@RequestMapping("/api/v1/servicestiers")
@RequiredArgsConstructor
public class cmiService {


    private final WalletClient walletClient;



    @GetMapping("/doTransaction")
    public  ResponseEntity<CmiResponse> doTransaction(@RequestBody CmiRequest cmiRequest){

        WalletResponse walletSender = walletClient.getWalletByIdClient(cmiRequest.senderId()).getBody();
        WalletResponse walletBeneficiary = walletClient.getWalletByIdClient(cmiRequest.beneficiaryId()).getBody();

        BigDecimal bigDecimalAmount = cmiRequest.amount();
        double amount = bigDecimalAmount.doubleValue();


        if (walletSender.balance() >= amount) {
            WalletRequest walletSenderUpdate = new WalletRequest(walletSender.id(),walletSender.balance()-amount,walletSender.clientId());
            WalletRequest walletBeneficiaryUpdate = new WalletRequest(walletBeneficiary.id(),walletBeneficiary.balance()+amount,walletBeneficiary.clientId());
            walletClient.updateWallet(walletSenderUpdate);
            walletClient.updateWallet(walletBeneficiaryUpdate);

            return ResponseEntity.ok(new CmiResponse(true));
        } else {
            return ResponseEntity.ok(new CmiResponse(false));
        }
    }


}
