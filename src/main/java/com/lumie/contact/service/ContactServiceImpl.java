package com.lumie.contact.service;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.entity.Tag;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.repository.ContactRepository;
import com.lumie.contact.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;
    private TagRepository tagRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, TagRepository tagRepository) {
        this.contactRepository = contactRepository;
        this.tagRepository = tagRepository;
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
        String[] tags = contact.getTag().split("([\\W]+)");

        List<String> tagsFromDB = tagRepository.findAll()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        List<String> tagsFromForm = Arrays.stream(tags)
                .distinct()
                .collect(Collectors.toList());

        List<String> uniqueTagList = tagsFromForm.stream()
                .filter(l -> !tagsFromDB.contains(l))
                .collect(Collectors.toList());

        if (!uniqueTagList.isEmpty()) {
            for (String s : uniqueTagList) {
                Tag tag = new Tag();
                tag.setTagName(s);
                tag.getContacts().add(contact);
//                    contact.getTags().add(tag);
                tagRepository.save(tag);
            }
        } else {
            contact.setTags(tagsFromForm.stream().map(t -> {
            Tag tag1 = new Tag();
            tag1.setTagName(t.toUpperCase());
                tag1.getContacts().add(contact);
            return tag1;
            }).collect(Collectors.toList()));

        }

        tagsFromForm.stream().map(t -> {
            Tag tag1 = new Tag();
            tag1.setTagName(t.toUpperCase());
            return tag1;
        }).collect(Collectors.toList());

//        contact.setTags(tagsFromForm.stream().map(t -> {
//            Tag tag1 = new Tag();
//            tag1.setTagName(t.toUpperCase());
//            return tag1;
//        }).collect(Collectors.toList()));

//        contact.addTag();
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
