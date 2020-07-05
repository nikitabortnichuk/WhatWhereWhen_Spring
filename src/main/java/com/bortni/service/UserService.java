package com.bortni.service;

import com.bortni.model.entity.User;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        LOGGER.debug("User saved");
    }

    public User findByUsername(String username){
        LOGGER.info("Searching for user by username");
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
    }

}
