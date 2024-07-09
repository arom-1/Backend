package com.example.arom1.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String content;
    private double rating;
    private int views;
    private int likes;
    private int dislikes;

}

