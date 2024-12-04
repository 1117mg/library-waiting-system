package com.example.librarywatingsystem.service;

import com.example.librarywatingsystem.model.User;
import com.example.librarywatingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> fineByUsername(String username){
        return userRepository.findByUsername(username);
    }
}