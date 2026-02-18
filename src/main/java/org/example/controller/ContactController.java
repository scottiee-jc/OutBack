package org.example.controller;

import org.example.model.Contact;
import org.example.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact,
                                                 @RequestAttribute Long tenantId) {
        Contact created = contactService.createContact(contact, tenantId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(
            @RequestAttribute Long tenantId,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        if (tag != null && !tag.isEmpty()) {
            List<Contact> taggedContacts = contactService.getContactsByTenantAndTags(tenantId, tag);
            return ResponseEntity.ok(new org.springframework.data.domain.PageImpl<>(taggedContacts));
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Contact> contacts = contactService.getContactsByTenant(tenantId, pageable);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable Long id,
                                              @RequestAttribute Long tenantId) {
        Contact contact = contactService.findById(id, tenantId);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id,
                                                 @Valid @RequestBody Contact updatedContact,
                                                 @RequestAttribute Long tenantId) {
        Contact contact = contactService.updateContact(id, updatedContact, tenantId);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id,
                                              @RequestAttribute Long tenantId) {
        contactService.deleteContact(id, tenantId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Contact>> bulkCreateContacts(@Valid @RequestBody List<Contact> contacts,
                                                            @RequestAttribute Long tenantId) {
        List<Contact> created = contactService.bulkCreateContacts(contacts, tenantId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}

