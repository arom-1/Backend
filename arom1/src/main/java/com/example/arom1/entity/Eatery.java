package com.example.arom1.entity;

import com.example.arom1.dto.SeoulEateryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //지번주소
    private String siteWhlAddr;

    //도로명 주소
    private String rdnWhlAdd;

    //도로명 우편주소
    private String rdnPostNo;

    //데이터 갱신일자
    private String updateDt;

    //업태구분명
    private String uptaeNm;

    @Column(name = "telephone", nullable = false)
    private String telephone;


    @Column(name = "rating", nullable = true)
    private double rating;

//    @Column(name = "information", nullable = false)
//    private String information;
//
//
//    @Column(name = "opening_hours", nullable = false)
//    private LocalDateTime openingHours;
//
//    @Column(name = "closing_hours", nullable = false)
//    private LocalDateTime closingHours;

    @OneToMany(mappedBy = "eatery")
    private List<Review> reviews = new ArrayList<>(); //양방향

    @OneToMany(mappedBy = "eatery")
    private List<EateryCategory> eateryCategories = new ArrayList<>(); //양방향

    @OneToMany(mappedBy = "eatery")
    private List<ChatRoom> chatRooms = new ArrayList<>(); //양방향

    @Builder
    public Eatery(String name, String siteWhlAddr, String rdnWhlAdd, String rdnPostNo, String updateDt, String uptaeNm, String telephone) {
        this.name = name;
        this.siteWhlAddr = siteWhlAddr;
        this.rdnWhlAdd = rdnWhlAdd;
        this.rdnPostNo = rdnPostNo;
        this.updateDt = updateDt;
        this.uptaeNm = uptaeNm;
        this.telephone = telephone;
    }

    public static Eatery dtoToEntity(SeoulEateryDto dto){
        return Eatery.builder()
                .name(dto.getName())
                .siteWhlAddr(dto.getSiteWhlAddr())
                .rdnWhlAdd(dto.getRdnWhlAddr())
                .rdnPostNo(dto.getRdnPostNo())
                .updateDt(dto.getUpdateDt())
                .uptaeNm(dto.getUptaeNm())
                .telephone(dto.getTelephone())
                .build();
    }
}
