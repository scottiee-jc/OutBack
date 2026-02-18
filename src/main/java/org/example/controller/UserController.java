package org.example.controller;

import org.example.model.Tenant;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String email,
                                             @RequestParam String password,
                                             @RequestParam Long tenantId) {
        // Create a new user and associate with a tenant
        Tenant tenant = new Tenant();
        User user = userService.registerUser(email, password, tenant);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<User>> getUsersByTenant(@PathVariable Long tenantId) {
        Tenant tenant = new Tenant();
        List<User> users = userService.getUsersByTenant(tenant);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestParam String oldPassword,
                                               @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String token) {
        userService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
