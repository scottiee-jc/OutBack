package org.example.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import jakarta.validation.constraints.NotBlank;

@Service
public class EmailService {

    private final SesClient sesClient;
    private final SessionTokenService sessionTokenService;

    public EmailService(SesClient sesClient, SessionTokenService sessionTokenService) {
        this.sesClient = sesClient;
        this.sessionTokenService = sessionTokenService;
    }

    public void sendEmail(@NotBlank String to, @NotBlank String subject, @NotBlank String body) {
        try {
            SendEmailRequest request = createSendEmailRequest(to, subject, body);
            sesClient.sendEmail(request);
        } catch (SesException e) {
            // Log the error and rethrow a custom exception
            throw new RuntimeException("Failed to send email: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    private SendEmailRequest createSendEmailRequest(String to, String subject, String body) {
        return SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(to).build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).build())
                        .body(Body.builder().text(Content.builder().data(body).build()).build())
                        .build())
                .source("your-email@example.com")
                .build();
    }
}