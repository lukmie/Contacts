package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.exception.ContactNotFoundException;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts();
    Contact getContactById(Long theId) throws ContactNotFoundException;
    void saveContact(Contact theContact);
    void deleteContact(Long theId) throws ContactNotFoundException;
    List<Contact> searchBy(String theFirstName);
}
