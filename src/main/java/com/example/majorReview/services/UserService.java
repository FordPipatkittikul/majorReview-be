package com.example.majorReview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


import com.example.majorReview.repositories.UserRepository;
import com.example.majorReview.models.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(String email,String name, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(hashedPassword);

        return userRepository.save(newUser);
    }

    public User findByEmailAndPassword(String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password,user.getPassword())) {
            return user;
        } else {
            return null;
        }

    }

    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);  // Return null if user not found
    }

}



