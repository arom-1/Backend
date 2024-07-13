package com.example.arom1.repository;

import com.example.arom1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    public List<Review> findByEateryId (Long eateryId);
}
