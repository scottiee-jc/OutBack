package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final SessionTokenService sessionTokenService;
    private final UserService userService;

    public AuthService(SessionTokenService sessionTokenService, UserService userService) {
        this.sessionTokenService = sessionTokenService;
        this.userService = userService;
    }

    public String login(String email, String password) {
        // Validate user credentials
        User user = userService.validateUser(email, password);
        if (user != null) {
            // Generate session token
            return sessionTokenService.generateSessionToken();
        }
        throw new RuntimeException("Invalid credentials");
    }
}
