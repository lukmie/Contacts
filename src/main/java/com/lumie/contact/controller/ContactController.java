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

    @GetMapping("/updateContact/{id}")
    public String updateContactForm(@PathVariable("id") Long id, Model theModel) throws ContactNotFoundException {
        Contact theContact = contactService.getContactById(id);
        theModel.addAttribute("contact", theContact);
        theModel.addAttribute("view", "Update contact");
        return "contact-form";
    }

    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact contact) throws ContactNotFoundException {
        contactService.saveContact(contact);
        return "redirect:/contacts/list";
    }

    @PostMapping("/updateContact")
    public String updateContact(@ModelAttribute("contact") Contact contact) throws ContactNotFoundException {
        contactService.updateContact(contact);
        return "redirect:/contacts/list";
    }

    @PostMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable("id") Long id) throws ContactNotFoundException {
        contactService.deleteContact(id);
        return "redirect:/contacts/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model) {
        if (search.trim().isEmpty()) {
            return "redirect:/contacts/list";
        } else {
            List<Contact> contacts = contactService.searchBy(search);
            model.addAttribute("contacts", contacts);
            return "contact-list";
        }
    }
}
