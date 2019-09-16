package com.lumie.contact.service;

import com.lumie.contact.dto.UserRegistrationDto;
import com.lumie.contact.entity.User;
import com.lumie.contact.exception.EmailAlreadyExistException;
import com.lumie.contact.exception.LoginAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User addUser(UserRegistrationDto user) throws EmailAlreadyExistException, LoginAlreadyExistException;

    Optional<User> findByLogin(String login);
}
