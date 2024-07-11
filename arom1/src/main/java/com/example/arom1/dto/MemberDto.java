package com.example.arom1.dto;

import com.example.arom1.entity.Member;
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
}
