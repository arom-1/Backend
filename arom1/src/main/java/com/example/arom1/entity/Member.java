package com.example.arom1.entity;

import com.example.arom1.dto.MemberDto;
import com.example.arom1.dto.MyPageDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
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

//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JsonBackReference
//    @JoinColumn(name = "member_location_id")
//    private Location location;

    public enum Gender {
        MALE, FEMALE
    }


    @Builder
    private Member(String email, String password, String name, String introduction, Gender gender, int age, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.gender = gender;
        this.age = age;
        this.nickname = nickname;
    }

    public void updateMyPage(MyPageDto dto) {
        this.introduction = dto.getIntroduction();
        this.age = dto.getAge();
        this.nickname = dto.getNickname();
    }

    public static Member createMember(MemberDto dto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))  //암호화처리
                .name(dto.getName())
                .introduction(dto.getIntroduction())
                .age(dto.getAge())
                .nickname(dto.getNickname())
                .build();
        return member;
    }

    public static boolean isLogin() {
        boolean result = true;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            result = false;
        }

        return result;
    }
}