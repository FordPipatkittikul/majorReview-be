package com.example.majorReview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.majorReview.services.UserService;
import com.example.majorReview.models.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> httpLogin(@RequestBody User user,
                                               @RequestHeader("User-Agent") String userAgent) {
        try{

            User isValidEmailAndPassword = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if(isValidEmailAndPassword == null){
                return new ResponseEntity<>(
                        "{\"msg\": \"Invalid credentials\"}",
                        HttpStatus.UNAUTHORIZED
                );
            }

            // Return a response by JSON and HTTP status message 200
            return new ResponseEntity<>(
                    "{\"msg\": \"successfully login\"}",
                    HttpStatus.OK
            );

        } catch (Exception e) {

            // Log the error for debugging purposes
            System.err.println("Error while login: " + e.getMessage());

            // Return a failure response with JSON and HTTP status message 500
            return new ResponseEntity<>(
                    "{\"msg\": \"Fail to log in\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> httpRegister(@RequestBody User user,
                                               @RequestHeader("User-Agent") String userAgent) {

        try {

            // Try to create and save the user in the database
            User newUser = userService.createUser(user.getEmail(),user.getName(), user.getPassword());

            // Return a response by JSON and HTTP status message 201
            return new ResponseEntity<>(
                    "{\"msg\": \"User created successfully\"}",
                    HttpStatus.CREATED
            );
        } catch (Exception e) {

            // Log the error for debugging purposes
            System.err.println("Error while registering user: " + e.getMessage());

            // Return a failure response with JSON and HTTP status message 500
            return new ResponseEntity<>(
                    "{\"msg\": \"Fail to create user\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


}

/* quick note
*
* - ResponseEntity<Object> and ResponseEntity<String> are both used to represent HTTP responses in a Spring REST controller,
* but they serve slightly different purposes based on the type of response body you want to return.
*
* - @Autowired is used to inject dependencies into beans. Normally used for variables.
* If you don't put @Autowired on the userService attribute in your AuthController,
* Spring will not automatically inject an instance of UserService into the controller.
* This will result in the userService field remaining null,
* leading to a NullPointerException when you try to call any methods on it, such as userService.createUser().
*
* */
