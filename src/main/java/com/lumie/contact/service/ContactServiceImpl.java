package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.entity.Tag;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @Transactional
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @Override
    @Transactional
    public Contact getContactById(Long theId) throws ContactNotFoundException {
        return contactRepository.findById(theId)
                .orElseThrow(() -> new ContactNotFoundException("Contact with id: " + theId + " does not exist."));
    }

    @Override
    @Transactional
    public void saveContact(Contact theContact) {
        contactRepository.save(theContact);
    }

    @Override
    @Transactional
    public void deleteContact(Long theId) throws ContactNotFoundException {
        if (contactRepository.findById(theId).get() == null){
            throw new ContactNotFoundException("Contact with id: " + theId + " does not exist.");
        }
        Optional<Contact> contact = contactRepository.findById(theId);
        contact.get().removeTag();
        contactRepository.delete(contact.get());
    }

    @Override
    public List<Contact> searchBy(String theFirstName) {
        return contactRepository.findByFirstNameContainsAllIgnoreCase(theFirstName);
    }
}