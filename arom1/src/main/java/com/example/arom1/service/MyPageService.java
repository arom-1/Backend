package com.example.arom1.service;


import com.example.arom1.dto.MyPageDto;
import com.example.arom1.entity.Member;
import com.example.arom1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;

    public MyPageDto updateById(Long id, MyPageDto myPageDto) {
        Member updatedMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 멤버가 존재하지 않습니다."));

        updatedMember.updateMyPage(myPageDto);

        memberRepository.save(updatedMember);

        return myPageDto;
    }


}
