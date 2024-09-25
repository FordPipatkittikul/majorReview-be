package com.example.majorReview.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import com.example.majorReview.services.UserService;
import com.example.majorReview.models.User;
import com.example.majorReview.utils.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> httpLogin(@RequestBody User user,
                                               @RequestHeader("User-Agent") String userAgent,
                                               HttpServletResponse response) {
        try{

            User userExist = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
            String token = JwtUtils.generateToken(userExist, "7d");

            if(userExist == null){
                return new ResponseEntity<>(
                        "{\"msg\": \"Invalid credentials\"}",
                        HttpStatus.UNAUTHORIZED
                );
            }

            Cookie jwtCookie = new Cookie("token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(604800);  // Set expiration time (7 days = 604800 seconds)
            response.addCookie(jwtCookie); // Add the cookie to the response

            // Return a response by JSON and HTTP status message 200
            return new ResponseEntity<>(
                    userExist,
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

            // Create and save the user in the database
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

    @PostMapping("/logout")
    public ResponseEntity<Object> httpLogout(HttpServletResponse response) {

        // in order to delete cookie or which is logout,
        // we need to create a new cookie with the same name, set its max age to zero.
        // I know it is weird.
        Cookie jwtCookie = new Cookie("token", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);  // Invalidate cookie
        response.addCookie(jwtCookie);

        return new ResponseEntity<>(
                "{\"msg\": \"Successfully logout\"}",
                HttpStatus.OK
        );


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
