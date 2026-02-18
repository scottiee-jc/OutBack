package org.example.service;

import org.example.exception.UnauthorizedException;
import org.example.model.Tenant;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.security.PasswordEncoderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Tenant testTenant;

    @BeforeEach
    void setUp() {
        testTenant = new Tenant();
        testTenant.setId(1L);
        testTenant.setName("Test Tenant");

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword");
        testUser.setTenant(testTenant);
        testUser.setRole("USER");
    }

    @Test
    void testRegisterUserSuccess() {
        String email = "newuser@example.com";
        String rawPassword = "password123";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoderService.encodePassword(rawPassword)).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.registerUser(email, rawPassword, testTenant);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        String email = "existing@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(RuntimeException.class, () ->
            userService.registerUser(email, "password", testTenant)
        );
    }

    @Test
    void testValidateUserSuccess() {
        String email = "test@example.com";
        String rawPassword = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(passwordEncoderService.matches(rawPassword, testUser.getPassword())).thenReturn(true);

        User result = userService.validateUser(email, rawPassword);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testValidateUserInvalidPassword() {
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(passwordEncoderService.matches(wrongPassword, testUser.getPassword())).thenReturn(false);

        assertThrows(UnauthorizedException.class, () ->
            userService.validateUser(email, wrongPassword)
        );
    }

    @Test
    void testChangePasswordSuccess() {
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoderService.matches(oldPassword, testUser.getPassword())).thenReturn(true);
        when(passwordEncoderService.encodePassword(newPassword)).thenReturn("hashedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.changePassword(1L, oldPassword, newPassword);

        verify(userRepository).save(any(User.class));
    }
}

