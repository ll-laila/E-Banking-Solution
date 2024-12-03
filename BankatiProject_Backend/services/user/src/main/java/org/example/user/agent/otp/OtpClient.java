package org.example.user.agent.otp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="otp-service", url = "${application.config.otp-url}")
public interface OtpClient {

    // Envoie un email contenant un OTP
    @PostMapping  // Envoie une requête HTTP POST
    public String sendEmail(@RequestBody SendEmailRequest sendEmailRequest);
}

