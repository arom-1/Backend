package com.example.arom1.dto.response;

import com.example.arom1.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private String content;
    private double rating;
    private int views;
    private int likes;
    private int dislikes;

    @Builder
    public ReviewResponse(Long id, String content, double rating, int views, int likes, int dislikes){
        this.id=id;
        this.content=content;
        this.rating=rating;
        this.views=views;
        this.likes=likes;
        this.dislikes=dislikes;
    }

    public static ReviewResponse entityToDto(Review review){
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .views(review.getViews())
                .likes(review.getLikes())
                .dislikes(review.getDislikes())
                .build();
    }

}
