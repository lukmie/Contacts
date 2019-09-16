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

        // work, family
        List<String> tagsFromDB = tagRepository.findAll()
                .stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        // asd, xx, work
        List<String> tagsFromForm = Arrays.stream(tags)
                .distinct()
                .collect(Collectors.toList());

        // asd, xx
        List<Tag> uniqueTagList = tagsFromForm.stream()
                .filter(l -> !tagsFromDB.contains(l))
                .map(t -> {
                    Tag tag = new Tag();
                    tag.setTagName(t.toUpperCase());
                    return tag;
                }).collect(Collectors.toList());

        // work
        List<Tag> existingDBTagList = tagsFromForm.stream()
                .filter(l -> tagsFromDB.contains(l))
                .map(t -> tagRepository.findByTagName(t))
                .collect(Collectors.toList());

        if (!uniqueTagList.isEmpty()) {
            uniqueTagList.forEach(t -> contact.getTags().add(t));
            uniqueTagList.forEach(t -> t.getContacts().add(contact));
            contactRepository.save(contact);
            uniqueTagList.forEach(t -> tagRepository.save(t));
        }

        if (!existingDBTagList.isEmpty()) {
            existingDBTagList.forEach(t -> contact.getTags().add(t));
            existingDBTagList.forEach(t -> t.getContacts().add(contact));
            contactRepository.save(contact);
        }

        System.out.println("================================================= uniqueTagList" + uniqueTagList);
        System.out.println("************************************************* existingDBTagList" + existingDBTagList);

        /*
        String s = tagsFromForm.get(0);
        Tag tag = new Tag();
        tag.setTagName(s);
//        tag.addContact();
        Tag byTagName = tagRepository.findByTagName(s);
        byTagName.getContacts().add(contact);
        contact.getTags().add(byTagName);
        contactRepository.save(contact);
        tagRepository.save(byTagName);
         */

//        if (!uniqueTagList.isEmpty()) {
//            for (String s : uniqueTagList) {
//                Tag tag = new Tag();
//                tag.setTagName(s);
//                tag.getContacts().add(contact);
//                tagRepository.save(tag);
//            }
//        } else {
//            contact.setTags(tagsFromForm.stream().map(t -> {
//            Tag tag1 = new Tag();
//            tag1.setTagName(t.toUpperCase());
//                tag1.getContacts().add(contact);
//            return tag1;
//            }).collect(Collectors.toList()));
//        }
//
//        List<Tag> collect = tagsFromForm.stream().map(t -> {
//            Tag tag1 = new Tag();
//            tag1.setTagName(t.toUpperCase());
//            return tag1;
//        }).collect(Collectors.toList());
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
