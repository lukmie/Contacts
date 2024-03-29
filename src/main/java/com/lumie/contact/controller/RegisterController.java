package com.lumie.contact.controller;

import com.lumie.contact.dto.UserRegistrationDto;
import com.lumie.contact.entity.User;
import com.lumie.contact.exception.EmailAlreadyExistException;
import com.lumie.contact.exception.LoginAlreadyExistException;
import com.lumie.contact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult bindResult) throws LoginAlreadyExistException, EmailAlreadyExistException {
        if (userService.findByLogin(userDto.getLogin()).isPresent()) {
            bindResult.rejectValue("login", null, "Account with this login already exist.");
        }

        if (bindResult.hasErrors())
            return "register-form";
        else {
            userService.addUser(userDto);
            return "redirect:/login-form";
        }
    }
}
