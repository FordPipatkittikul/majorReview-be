package com.example.majorReview.controllers;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.majorReview.services.UserService;
import com.example.majorReview.services.ReviewService;
import com.example.majorReview.models.Review;
import com.example.majorReview.utils.JwtUtils;
import com.example.majorReview.models.User;



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> createReview (@RequestBody Review review,
                                            @RequestHeader("User-Agent") String userAgent,
                                                @CookieValue("token") String jwtToken){
        try {

            String userId = JwtUtils.getUserIdFromToken(jwtToken);
            User user = userService.findUserById(Long.parseLong(userId));

//            System.out.println(jwtToken); // correct token
//            System.out.println(userId); // correct user_id

            // Create and save review in the database
            Review reviewInfo = reviewService.createReview(review.getMajorName(),review.getQuality(),review.getDifficulty(),review.getReview(),user);


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"msg\": \"review created successfully\"}");

        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error while create review: " + e.getMessage());

            // Return a failure response with JSON and HTTP status message 500
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"msg\": \"Fail to create review\"}");
        }

    }

    @GetMapping("/{majorName}")
    public ResponseEntity<Object> getReviewByMajorName (@PathVariable String majorName,
                                                @RequestHeader("User-Agent") String userAgent){
        try {

            List<Review> reviewList = reviewService.findReviewByMajorName(majorName);
            System.out.println(reviewList);

            return new ResponseEntity<>(
                    reviewList,
                    HttpStatus.OK
            );

        } catch (Exception e) {

            // Log the error for debugging purposes
            System.err.println("Error while create review: " + e.getMessage());

            // Return a failure response with JSON and HTTP status message 500
            return new ResponseEntity<>(
                    "{\"msg\": \"Fail to get review\"}",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReview(@RequestBody Review review, @PathVariable Long id){
        try {
            reviewService.updateReviewById(id,review);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"msg\": \"review update successfully\"}");
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error while update review: " + e.getMessage());

            // Return a failure response with JSON and HTTP status message 500
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"msg\": \"Fail to update review\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


}
