package com.bortni.service;

import com.bortni.model.entity.User;
import com.bortni.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User findByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password).get(); // todo exception
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).get(); // todo exception
    }

}
