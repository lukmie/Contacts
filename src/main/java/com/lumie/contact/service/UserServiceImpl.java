package com.lumie.contact.service;

import com.lumie.contact.dto.UserRegistrationDto;
import com.lumie.contact.entity.User;
import com.lumie.contact.entity.UserRole;
import com.lumie.contact.exception.EmailAlreadyExistException;
import com.lumie.contact.exception.LoginAlreadyExistException;
import com.lumie.contact.repository.UserRepository;
import com.lumie.contact.repository.UserRoleRepository;
import com.lumie.contact.service.mapper.UserRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(UserRegistrationDto userRegistrationDto) throws EmailAlreadyExistException, LoginAlreadyExistException {
//        userRepository.findByEmail(userRegistrationDto.getEmail())
//                .orElseThrow(() -> new EmailAlreadyExistException("Email address " + userRegistrationDto.getEmail() + " already exist."));
//        userRepository.findByLogin(userRegistrationDto.getLogin())
//                .orElseThrow(() -> new LoginAlreadyExistException("Login " + userRegistrationDto.getEmail() + " already exist."));
        // todo setup new user add role map to entity and add to DB
        //
        User user = UserRegistrationMapper.map(userRegistrationDto);
        UserRole defaultRole = userRoleRepository.findByName(DEFAULT_ROLE);
        user.getUserRoles().add(defaultRole);
//        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        return userRepository.save(user);
    }
}
