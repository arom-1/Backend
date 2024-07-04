package com.example.team1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "eatery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Eatery extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eatery_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "information", nullable = false)
    private String information;

    @Column(name = "rating", nullable = true)
    private double rating;

    @Column(name = "opening_hours", nullable = false)
    private LocalDateTime openingHours;

    @Column(name = "closing_hours", nullable = false)
    private LocalDateTime closingHours;

    @OneToMany(mappedBy = "eatery")
    private List<Review> reviews = new ArrayList<>(); //양방향

    @OneToMany(mappedBy = "eatery")
    private List<EateryCategory> eateryCategories = new ArrayList<>(); //양방향

    @OneToMany(mappedBy = "eatery")
    private List<ChatRoom> chatRooms = new ArrayList<>(); //양방향



}
