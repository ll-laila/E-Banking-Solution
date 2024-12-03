package org.example.user.agent.otp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SendEmailRequest {
    private String toEmail;
    private String subject;
    private String body;
}

