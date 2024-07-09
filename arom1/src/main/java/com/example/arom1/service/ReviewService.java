package com.example.arom1.service;

import com.example.arom1.dto.ReviewDto;
import com.example.arom1.entity.Board;
import com.example.arom1.entity.Review;
import com.example.arom1.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    //리뷰 작성 기능
    public Review createReview(ReviewDto dto) {
        Review newReview = Review.newReview(dto); // Review 엔티티의 빌더를 사용하여 새 리뷰 생성
        return reviewRepository.save(newReview); // 생성된 리뷰를 저장하고 반환
    }

    //리뷰 수정 기능, Review @Builder에 id 추가
    public Review updateReview(Long id, ReviewDto reviewDto) {
        Review existingReview = reviewRepository.findById(id).orElse(null);

        Review updatedReview = Review.builder()
                .id(existingReview.getId())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .views(reviewDto.getViews())
                .likes(reviewDto.getLikes())
                .dislikes(reviewDto.getDislikes())
                .build();

        return reviewRepository.save(updatedReview);
    }
    
    //리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
