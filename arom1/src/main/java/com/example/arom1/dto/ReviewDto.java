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
    private long member_id;
    private long eatery_id;

    @Builder
    private ReviewDto(Long id,String content, double rating, int views, int likes, int dislikes, long member_id, long eatery_id) {
        this.id=id;
        this.content=content;
        this.rating=rating;
        this.views=views;
        this.likes=likes;
        this.dislikes=dislikes;
        this.member_id=member_id;
        this.eatery_id=eatery_id;
    }

}

