package org.example.service;

import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Create a new contact
    public Contact createContact(Contact contact) {
        if (contactRepository.existsByEmail(contact.getEmail())) {
            throw new IllegalArgumentException("A contact with this email already exists.");
        }
        return contactRepository.save(contact);
    }

    // Update an existing contact
    public Contact updateContact(Long id, Contact updatedContact) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setName(updatedContact.getName());
        contact.setEmail(updatedContact.getEmail());
        contact.setTags(updatedContact.getTags());
        return contactRepository.save(contact);
    }

    // Delete a contact
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found");
        }
        contactRepository.deleteById(id);
    }

    // Find contacts by tag
    public List<Contact> findContactsByTag(String tag) {
        return contactRepository.findByTagsContaining(tag);
    }
}