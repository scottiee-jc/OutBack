package org.example.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionTokenService {

    private static final long TOKEN_EXPIRY_DURATION = 3600_000; // 1 hour in milliseconds
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    // Generate a new session token
    public String generateSessionToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        tokenStore.put(token, System.currentTimeMillis() + TOKEN_EXPIRY_DURATION);
        return token;
    }

    // Validate a session token
    public boolean validateSessionToken(String token) {
        Long expiryTime = tokenStore.get(token);
        if (expiryTime == null || expiryTime < System.currentTimeMillis()) {
            tokenStore.remove(token); // Remove expired token
            return false;
        }
        return true;
    }

    // Revoke a session token
    public void revokeSessionToken(String token) {
        tokenStore.remove(token);
    }
}
