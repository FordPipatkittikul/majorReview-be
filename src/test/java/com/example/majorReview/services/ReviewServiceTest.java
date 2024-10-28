package com.example.majorReview.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.majorReview.models.Review;
import com.example.majorReview.models.User;
import com.example.majorReview.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserRepository userRepository;

	private User testUser;

	@BeforeEach
	public void setUp() {
		testUser = new User();
		testUser.setId(1L);
		testUser.setEmail("testuser@gmail.com");
		testUser.setName("testuser");
		testUser.setPassword("testuser123");
		userRepository.save(testUser);
	}

	@Test
	public void testCreateReview() {
		Review review = reviewService.createReview("Finance", 5, 3, "Great major!", testUser);

		assertNotNull(review);
		assertEquals("Finance", review.getMajorName());
		assertEquals(5, review.getQuality());
		assertEquals(3, review.getDifficulty());
		assertEquals("Great major!",review.getReview());
	}

	@Test
	public void testCreateReviewWithInvalidData() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			reviewService.createReview("Computer Science", -1, 3, "Great major!", testUser);
		});
	}

	@Test
	public void testFindReviewByMajorName() {
		Review review = reviewService.createReview("Computer Science", 5, 3, "Great major!", testUser);
		Review review2 = reviewService.createReview("Computer Science", 4, 4, "ok major!", testUser);
		List<Review> reviews = reviewService.findReviewByMajorName("Computer Science");

		assertEquals(2, reviews.size());
		assertEquals("Computer Science", reviews.get(0).getMajorName());
		assertEquals(4, reviews.get(1).getQuality());
		assertEquals(3, reviews.get(0).getDifficulty());
		assertEquals("ok major!", reviews.get(1).getReview());
	}

	@Test
	public void testFindReviewByNonExistentMajorName() {
		List<Review> reviews = reviewService.findReviewByMajorName("Non-Existent Major");
		assertTrue(reviews.isEmpty());
	}

}
