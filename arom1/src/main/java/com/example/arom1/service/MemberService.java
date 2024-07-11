package com.example.arom1.service;

import com.example.arom1.dto.MemberDto;
import com.example.arom1.entity.Member;
import com.example.arom1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public Long save(MemberDto dto) {
        return memberRepository.save(Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .introduction(dto.getIntroduction())
                .build()).getId();
    }
}
