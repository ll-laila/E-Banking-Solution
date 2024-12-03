package org.example.user.portefeuille;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="", url = "${}")
public interface PortefeuilleClient {

    @PostMapping("/addPortefeuille")
    public Long savePortefeuille(@RequestBody Portefeuille portefeuille);

    @GetMapping("/getPortefeuille")
    public Long getPortefeuille(@RequestBody Long portefeuilleId);
}
