package com.example.arom1.dto.response;

import com.example.arom1.entity.Eatery;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EateryResponse {
    private String name;
    //지번주소
    private String siteWhlAddr;
    //도로명 주소
    private String rdnWhlAdd;
    //도로명 우편주소
    private String rdnPostNo;
    //업태구분명
    private String uptaeNm;

    private String telephone;

    private double rating;

    @Builder
    private EateryResponse(String name, String siteWhlAddr, String rdnWhlAdd, String rdnPostNo, String uptaeNm, String telephone, double rating) {
        this.name = name;
        this.siteWhlAddr = siteWhlAddr;
        this.rdnWhlAdd = rdnWhlAdd;
        this.rdnPostNo = rdnPostNo;
        this.uptaeNm = uptaeNm;
        this.telephone = telephone;
        this.rating = rating;
    }

    public static EateryResponse entityToDto(Eatery eatery){
        return EateryResponse.builder()
                .name(eatery.getName())
                .siteWhlAddr(eatery.getSiteWhlAddr())
                .rdnWhlAdd(eatery.getRdnWhlAdd())
                .rdnPostNo(eatery.getRdnPostNo())
                .uptaeNm(eatery.getUptaeNm())
                .telephone(eatery.getTelephone())
                .rating(eatery.getRating())
                .build();
    }

}