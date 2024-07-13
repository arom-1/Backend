package com.example.arom1.dto.request;

import com.example.arom1.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewCreateRequest {

    private String content;
    private double rating;
    private int views;
    private int likes;
    private int dislikes;


    public Review toEntity(){
        return Review.builder()
                .content(content)
                .rating(rating)
                .views(views)
                .likes(likes)
                .dislikes(dislikes)
                .build();
    }
}
