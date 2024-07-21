package com.example.arom1.dto.response;

import com.example.arom1.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private String name;
    private int age;


    public static MemberResponse fromEntity(Member member) {
        MemberResponse response = new MemberResponse();
        response.setName(member.getName());
        response.setAge(member.getAge());
        return response;
    }

}
