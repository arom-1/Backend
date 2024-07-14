package com.example.arom1.entity;

import com.example.arom1.dto.MyPageDto;
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
public class Member extends BaseEntity implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("member"));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // true -> 잠금되지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true -> 만료되지 않음
    }

    // 계정 사용 가능 여부 변환
    @Override
    public boolean isEnabled() {
        return true; // true -> 사용 가능
    }

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

    public void updateMyPage(MyPageDto dto) {
        this.introduction = dto.getIntroduction();
        this.age = dto.getAge();
        this.nickname = dto.getNickname();
    }
}