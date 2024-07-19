package com.example.arom1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;

    private String email;

    private String password;

    private String name;

    private String introduction;

    private int age;

    private String nickname;

    @Builder
    private MemberDto(String email, String password, String name, String introduction, int age, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.age = age;
        this.nickname = nickname;
    }
}
