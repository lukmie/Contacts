package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

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
    public void saveContact(Contact contact) throws ContactNotFoundException {
//        Contact contactRepositoryId = contactRepository.findById(contact.getId()).orElseThrow(() -> new ContactNotFoundException("Error"));
        contact.addTag();
        contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void updateContact(Contact contact) throws ContactNotFoundException {
        Contact contactRepositoryId = contactRepository.findById(contact.getId()).orElseThrow(() -> new ContactNotFoundException("Error"));
        contactRepositoryId.setFirstName(contact.getFirstName());
        contactRepositoryId.setLastName(contact.getLastName());
        contactRepositoryId.setEmail(contact.getEmail());
        contactRepositoryId.setPhoneNumber(contact.getPhoneNumber());
        if (!contactRepositoryId.getTags().isEmpty()) {
            contactRepositoryId.removeTag();
        }
        contactRepositoryId.setTags(contact.getTags());
        contactRepositoryId.setTag(contact.getTag());
//        System.out.println("iddddddddddddddddddd" + contact.getId());
//        System.out.println("------------" + contact.getTags());
        contactRepositoryId.addTag();
        contactRepository.save(contactRepositoryId);
    }

    @Override
    @Transactional
    public void deleteContact(Long theId) throws ContactNotFoundException {
        Contact contact = contactRepository.findById(theId)
                .orElseThrow(() -> new ContactNotFoundException("Contact with id: " + theId + " does not exist."));
        contact.removeTag();
        contactRepository.delete(contact);
    }

    @Override
    public List<Contact> searchBy(String search) {
        return contactRepository.findAllByFirstNameOrLastName(search);
    }
}
