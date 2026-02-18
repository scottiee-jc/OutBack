package org.example.repository;

import org.example.model.Contact;
import org.example.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByEmail(String email);

    List<Contact> findByTagsContaining(String tag);
}
