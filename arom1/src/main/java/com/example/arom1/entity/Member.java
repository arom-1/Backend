package com.example.arom1.entity;

import com.example.arom1.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Member(String email, String password, String name, String introduction, Gender gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.gender = gender;
    }
}