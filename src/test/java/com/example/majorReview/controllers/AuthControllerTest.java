package com.example.majorReview.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.majorReview.services.UserService;
import com.example.majorReview.models.User;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Value("${SECURITY_NAME}")
    private String username;

    @Value("${SECURITY_PASS}")
    private String password;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setName("Test User");
        mockUser.setPassword("password123");

    }

    @Test
    void testHttpRegisterSuccess() throws Exception {
        Mockito.when(userService.createUser(any(String.class), any(String.class), any(String.class)))
                .thenReturn(mockUser);
        mockMvc.perform(post("/api/auth/register")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"name\":\"Test User\", \"password\":\"password123\"}")
                        .header("User-Agent", "Mozilla/5.0"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg").value("User created successfully"));
    }

    @Test
    void testHttpLoginSuccess() throws Exception {
        Mockito.when(userService.findByEmailAndPassword(any(String.class), any(String.class)))
                .thenReturn(mockUser);
        mockMvc.perform(post("/api/auth/login")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}")
                        .header("User-Agent", "Mozilla/5.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com")) // validate the user details returned
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void testHttpLogoutSuccess() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .with(httpBasic(username, password))
                        .header("User-Agent", "Mozilla/5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Successfully logout"));
    }

}
