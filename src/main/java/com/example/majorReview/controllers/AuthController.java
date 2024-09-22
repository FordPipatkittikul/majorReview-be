package com.example.majorReview.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.majorReview.models.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<String> httpRegister(@RequestBody User user,
                                               @RequestHeader("User-Agent") String userAgent) {
        // logging
        System.out.println("User-Agent: " + userAgent);
        System.out.println(user.getPassword());

        return new ResponseEntity<>("User " + user.getName() + " registered!", HttpStatus.CREATED);
    }

}
