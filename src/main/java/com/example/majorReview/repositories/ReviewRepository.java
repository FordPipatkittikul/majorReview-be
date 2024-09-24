package com.example.majorReview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.majorReview.models.Review;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMajorName(String majorName);
}
