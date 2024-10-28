package com.example.majorReview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.example.majorReview.models.User;
import com.example.majorReview.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final String TEST_EMAIL = "testuser@gmail.com";
    private static final String TEST_NAME = "testuser";
    private static final String TEST_PASSWORD = "testuser123";
    private User testUser;

    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(TEST_PASSWORD);
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail(TEST_EMAIL);
        testUser.setName(TEST_NAME);
        testUser.setPassword(hashedPassword);
        userRepository.save(testUser);
    }

    @Test
    public void testFindByEmailAndPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userService.findByEmailAndPassword("testuser@gmail.com","testuser123");

        assertNotNull(user);
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_NAME, user.getName());
        assertTrue(passwordEncoder.matches(TEST_PASSWORD,user.getPassword()));
    }

    @Test
    public void testFindNoneExistUserByEmailAndPassword() {
        User user = userService.findByEmailAndPassword("x@gmail.com","x123456");
        assertNull(user);
    }

    @Test
    public void testFindUserById() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userService.findUserById(1L);

        assertNotNull(user);
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_NAME, user.getName());
        assertTrue(passwordEncoder.matches(TEST_PASSWORD,user.getPassword()));
    }

    @Test
    public void testFindNoneExistUserById() {
        User user = userService.findUserById(5L);
        assertNull(user);
    }

}
