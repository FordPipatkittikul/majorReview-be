package com.example.majorReview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.majorReview.repositories.ReviewRepository;
import com.example.majorReview.models.Review;
import com.example.majorReview.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review findUserById(Long userId) {
        Optional<Review> review = reviewRepository.findById(userId);
        return review.orElse(null);  // Return null if user not found
    }

    public Review createReview(String majorName, int quality, int difficulty, String review, User user){
        Review reviewInfo = new Review();
        reviewInfo.setMajorName(majorName);
        reviewInfo.setQuality(quality);
        reviewInfo.setDifficulty(difficulty);
        reviewInfo.setReview(review);
        reviewInfo.setUser(user);
        return reviewRepository.save(reviewInfo);
    }

    public List<Review> findReviewByMajorName(String majorName) {
        return reviewRepository.findByMajorName(majorName);

    }

    public Review updateReviewById(Long id, Review updatedReview){
        Review existReview = findUserById(id);
        existReview.setQuality(updatedReview.getQuality());
        existReview.setDifficulty(updatedReview.getDifficulty());
        existReview.setReview(updatedReview.getReview());
        return reviewRepository.save(existReview);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById((id));
    }

}
