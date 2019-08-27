package com.lumie.contact.controller;

import com.lumie.contact.entity.Contact;
import com.lumie.contact.exception.ContactNotFoundException;
import com.lumie.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/list")
    public String showAllContacts(Model theModel) {
        List<Contact> contactList = contactService.getContacts();
        theModel.addAttribute("contacts", contactList);
        return "contact-list";
    }

    @GetMapping("/add")
    public String addNewContactForm(Model theModel) {
        Contact theContact = new Contact();
        theModel.addAttribute("contact", theContact);
        return "contact-form";
    }

    @GetMapping("/updateContact")
    public String updateContactForm(@RequestParam("theId") Long theId, Model theModel) throws ContactNotFoundException {
        Contact theContact = contactService.getContactById(theId);
        theModel.addAttribute("contact", theContact);
        return "contact-form";
    }

    @PostMapping("/saveContact")
    public String saveContact(@ModelAttribute("contact") Contact theContact) {
        contactService.saveContact(theContact);
        return "redirect:/contacts/list";
//        return "redirect:contact-list";
    }

    @GetMapping("/deleteContact")
    public String deleteContact(@RequestParam("theId") Long theId) throws ContactNotFoundException {
        contactService.deleteContact(theId);
        return "redirect:/contacts/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("firstName") String theFirstName, Model theModel) {
        if (theFirstName.trim().isEmpty()) {
            return "redirect:/contacts/list";
        } else {
            List<Contact> theEmployees =
                    contactService.searchBy(theFirstName);
            theModel.addAttribute("contacts", theEmployees);
            return "contact-list2";
        }
    }


}
