package com.lumie.contact.controller;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.repository.TagRepository;
import com.lumie.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/list")
    public String showAllContacts(Model theModel) {
        List<Contact> contactList = contactService.getContacts();
        theModel.addAttribute("contacts", contactList
                .stream()
                .sorted(Comparator.comparing(Contact::getLastName))
                .collect(Collectors.toList()));
        return "contact-list";
    }

    @GetMapping("/addContact")
    public String addNewContactForm(Model theModel) {
        Contact theContact = new Contact();
        theModel.addAttribute("contact", theContact);
        theModel.addAttribute("view", "Add contact");
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
