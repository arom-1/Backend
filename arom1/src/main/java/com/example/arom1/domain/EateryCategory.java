package com.example.team1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "eatery_category")
public class EateryCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //양방향
    @JoinColumn(name = "eatery_id", nullable = false)
    private com.example.team1.domain.Eatery eatery;

    @ManyToOne(fetch = FetchType.LAZY) //양방향
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}