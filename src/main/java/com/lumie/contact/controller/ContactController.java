package com.lumie.contact.controller;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.entity.Tag;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.service.ContactService;
import com.lumie.contact.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private ContactService contactService;
    private TagService tagService;

    @Autowired
    public ContactController(ContactService contactService, TagService tagService) {
        this.contactService = contactService;
        this.tagService = tagService;
    }

    @GetMapping("/list")
    public String showAllContacts(@RequestParam(required = false) String tag, Model model) {
        List<Contact> contactList;
        if (tag == null) {
            contactList = contactService.getContacts();
        } else {
            contactList = tagService.getTagsByTagName(tag);
        }
        model.addAttribute("contacts", contactList
                .stream()
                .sorted(Comparator.comparing(Contact::getLastName))
                .collect(Collectors.toList()));
        List<Tag> tagList = tagService.getTags();
        model.addAttribute("tags", tagList);
        return "contact-list";
    }

    @GetMapping("/addContact")
    public String addNewContactForm(Model model) {
        Contact theContact = new Contact();
        model.addAttribute("contact", theContact);
        model.addAttribute("view", "Add contact");
        return "contact-form";
    }

    @GetMapping("/updateContact")
    public String updateContactForm(@RequestParam("theId") Long theId, Model theModel) throws ContactNotFoundException {
        Contact theContact = contactService.getContactById(theId);
        theModel.addAttribute("contact", theContact);
        theModel.addAttribute("view", "Update contact");
        return "contact-form";
    }

    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact theContact) {
        contactService.saveContact(theContact);
        return "redirect:/contacts/list";
    }

    @GetMapping("/deleteContact")
    public String deleteContact(@RequestParam("theId") Long theId) throws ContactNotFoundException {
        contactService.deleteContact(theId);
        return "redirect:/contacts/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model theModel) {
        if (search.trim().isEmpty()) {
            return "redirect:/contacts/list";
        } else {
//            List<Contact> contacts = contactService.searchBy(firstName);
            List<Contact> contacts = contactService.searchBy(search);
            theModel.addAttribute("contacts", contacts);
            return "contact-list";
        }
    }


}
