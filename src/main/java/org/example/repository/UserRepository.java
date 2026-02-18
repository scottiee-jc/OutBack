package org.example.repository;

import org.example.model.Tenant;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findAllByTenant(Tenant tenant);

    Optional<User> findByEmail(String email);
}
