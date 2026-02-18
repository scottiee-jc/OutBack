package org.example.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderServiceTest {

    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    void setUp() {
        passwordEncoderService = new PasswordEncoderService();
    }

    @Test
    void testEncodePassword() {
        String rawPassword = "myPassword123";
        String encodedPassword = passwordEncoderService.encodePassword(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoderService.matches(rawPassword, encodedPassword));
    }

    @Test
    void testMatchesSuccess() {
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoderService.encodePassword(rawPassword);

        assertTrue(passwordEncoderService.matches(rawPassword, encodedPassword));
    }

    @Test
    void testMatchesFail() {
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoderService.encodePassword(rawPassword);

        assertFalse(passwordEncoderService.matches("wrongPassword", encodedPassword));
    }
}

