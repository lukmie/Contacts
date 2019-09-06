package com.lumie.contact.service.mapper;

import com.lumie.contact.dto.UserRegistrationDto;
import com.lumie.contact.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    private static BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static User map(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setLogin(userRegistrationDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
//        user.setBirthDate(userRegistrationDto.getBirthDate());
        user.setPesel(userRegistrationDto.getPesel());
        return user;
    }
}
