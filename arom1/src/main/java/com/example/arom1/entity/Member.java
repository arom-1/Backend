package com.example.arom1.entity;

import com.example.arom1.dto.MyPageDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String introduction;

    private int age;

    private String nickname;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "member_location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonBackReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ChatRoomMember> chatroomMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> BoardMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> BoardReplyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Image> images = new ArrayList<>();

    public enum Gender {
        MALE, FEMALE
    }


    @Builder
    private Member(String email, String password, String name, String introduction, int age, String nickname, Gender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.age = age;
        this.nickname = nickname;
        this.gender = gender;
    }

    public void updateMyPage(MyPageDto dto) {
        this.introduction = dto.getIntroduction();
        this.age = dto.getAge();
        this.nickname = dto.getNickname();
    }

}