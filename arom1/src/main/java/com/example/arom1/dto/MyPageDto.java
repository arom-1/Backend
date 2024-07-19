package com.example.arom1.dto;

import com.example.arom1.entity.Image;
import com.example.arom1.entity.Member;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MyPageDto {

    private String nickname;
    private int age;
    private String introduction;

    @Builder
    private MyPageDto(String nickname, int age, String introduction) {
        this.nickname = nickname;
        this.age = age;
        this.introduction = introduction;
    }

    public static MyPageDto newMyPageDto(Member member) {
        return MyPageDto.builder()
                .age(member.getAge())
                .introduction(member.getIntroduction())
                .nickname(member.getNickname())
                .build();
    }


}
