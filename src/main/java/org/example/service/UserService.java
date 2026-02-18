package org.example.service;

import org.example.model.Tenant;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {
    private final SessionTokenService sessionTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(SessionTokenService sessionTokenService, UserRepository userRepository) {
        this.sessionTokenService = sessionTokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    public User registerUser(String email, String password, Tenant tenant) {
        // Check if the email is already in use
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use");
        }

        // Create and save the new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Use a password encoder
        user.setTenant(tenant);
        user.setRole("USER"); // Default role
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByTenant(Tenant tenant) {
        return userRepository.findAllByTenant(tenant);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void assignRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }
    public void logout(String token) {
        sessionTokenService.revokeSessionToken(token);
    }

    public User validateUser(String email, String password) {
        // Retrieve the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check if the provided password matches the stored hashed password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Return the user if validation is successful
        return user;
    }
}
