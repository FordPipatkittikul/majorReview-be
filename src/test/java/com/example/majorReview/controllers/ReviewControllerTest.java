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
import jakarta.servlet.http.Cookie;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.majorReview.services.UserService;
import com.example.majorReview.services.ReviewService;
import com.example.majorReview.models.User;
import com.example.majorReview.models.Review;
import com.example.majorReview.utils.JwtUtils;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ReviewService reviewService;

    @Value("${SECURITY_NAME}")
    private String username;

    @Value("${SECURITY_PASS}")
    private String password;

    private User mockUser;

    private Review mockReview;

    @BeforeEach
    public void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setName("Test User");
        mockUser.setPassword("password123");

        mockReview = new Review();
        mockReview.setMajorName("Accounting");
        mockReview.setQuality(5);
        mockReview.setDifficulty(5);
        mockReview.setReview("It is ok");
        mockReview.setUser(mockUser);
    }

    @Test
    void testHttpCreateReview() throws Exception {

        Mockito.when(userService.findUserById(any(Long.class)))
                .thenReturn(mockUser);

        Mockito.when(reviewService.createReview(any(String.class), any(Integer.class), any(Integer.class), any(String.class), any(User.class)))
                .thenReturn(mockReview);

        String jsonContent = "{\"majorName\":\"Accounting\", \"quality\":5, \"difficulty\":5, \"review\":\"It is ok\"}";
        String token = JwtUtils.generateToken(mockUser, "7d");
        Cookie jwtCookie = new Cookie("token", token);

        mockMvc.perform(post("/api/reviews/")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .header("User-Agent", "Mozilla/5.0")
                        .cookie(jwtCookie))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"msg\": \"review created successfully\"}"));
    }

    @Test
    void testHttpGetReviewByMajorName() throws Exception {
        String majorName = "Computer Science";
        Review review1 = new Review();
        review1.setMajorName(majorName);
        review1.setQuality(5);
        review1.setDifficulty(5);
        review1.setReview("It is good");
        review1.setUser(mockUser);

        Review review2 = new Review();
        review1.setMajorName(majorName);
        review1.setQuality(3);
        review1.setDifficulty(3);
        review1.setReview("It is ok");
        review1.setUser(mockUser);

        List<Review> reviewList = Arrays.asList(review1, review2);

        mockMvc.perform(get("/api/reviews/{majorName}", majorName)
                        .with(httpBasic(username, password))
                        .header("User-Agent", "Mock User Agent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
