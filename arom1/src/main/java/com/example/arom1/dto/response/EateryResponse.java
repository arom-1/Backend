package com.example.arom1.dto.response;

import com.example.arom1.common.geo.DistanceCalculator;
import com.example.arom1.entity.Eatery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

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

    private double distance;

    @Builder
    private EateryResponse(String name, String siteWhlAddr, String rdnWhlAdd, String rdnPostNo, String uptaeNm, String telephone, double rating, double distance) {
        this.name = name;
        this.siteWhlAddr = siteWhlAddr;
        this.rdnWhlAdd = rdnWhlAdd;
        this.rdnPostNo = rdnPostNo;
        this.uptaeNm = uptaeNm;
        this.telephone = telephone;
        this.rating = rating;
        this.distance = distance;
    }

    public static EateryResponse entityToDto(Eatery eatery, Point member){
        double distance = DistanceCalculator.calculateDistance(eatery.getEateryLocation().getPoint(), member);
        return EateryResponse.builder()
                .name(eatery.getName())
                .siteWhlAddr(eatery.getSiteWhlAddr())
                .rdnWhlAdd(eatery.getRdnWhlAdd())
                .rdnPostNo(eatery.getRdnPostNo())
                .uptaeNm(eatery.getUptaeNm())
                .telephone(eatery.getTelephone())
                .rating(eatery.getRating())
                .distance(distance)
                .build();
    }

}