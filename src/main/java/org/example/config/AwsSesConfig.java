package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import org.example.service.SessionTokenService;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.time.Instant;

@Configuration
public class AwsSesConfig {

    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;

    private final SessionTokenService sessionTokenService;

    public AwsSesConfig(SessionTokenService sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @Bean
    public SesClient sesClient() {
        return SesClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.builder()
                                .accessKeyId(accessKey)
                                .secretAccessKey(secretKey)
                                .sessionToken(sessionTokenService.generateSessionToken())
                                .expirationTime(Instant.now().plusSeconds(3000))
                                .build()
                ))
                .build();
    }
}
