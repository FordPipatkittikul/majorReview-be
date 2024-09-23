package com.example.majorReview.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.majorReview.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom queries can be added here if needed
    User findByEmail(String email);
}
