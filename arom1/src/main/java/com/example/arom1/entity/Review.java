package com.example.arom1.entity;


import com.example.arom1.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.dialect.function.EveryAnyEmulation;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private double rating;

    private int views;

    private int likes;

    private int dislikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eatery_id")
    private Eatery eatery;


    @Builder
    private Review(Long id, String content, double rating, int views, int likes, int dislikes, Member member, Eatery eatery) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.views = views;
        this.likes = likes;
        this.dislikes = dislikes;
        this.eatery = eatery;
        this.member = member;
    }

    public static Review newReview(ReviewDto dto, Member member, Eatery eatery) {
        return Review.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .views(dto.getViews())
                .likes(dto.getLikes())
                .dislikes(dto.getDislikes())
                .member(member)
                .eatery(eatery)
                .build();
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setViews(int views){
        this.views = views;
    }

    public void setLikes(int likes){
        this.likes = likes;
    }

    public void setDislikes(int dislikes){
        this.dislikes = dislikes;
    }



}