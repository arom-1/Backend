package com.example.arom1.dto;


import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.Member;
import com.example.arom1.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReviewDto {
    private Long id;
    private String content;
    private double rating;
    private int views;
    private int likes;
    private int dislikes;
    private Member member;
    private Eatery eatery;

    @Builder
    private ReviewDto(Long id,String content, double rating, int views, int likes, int dislikes) {
        this.id=id;
        this.content=content;
        this.rating=rating;
        this.views=views;
        this.likes=likes;
        this.dislikes=dislikes;
    }

    public static ReviewDto reviewDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .views(review.getViews())
                .likes(review.getViews())
                .dislikes(review.getViews())
                .build();
    }

}

