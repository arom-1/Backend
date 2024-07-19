package com.example.arom1.service;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponseStatus;
import com.example.arom1.entity.Member;
import com.example.arom1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);

        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember.isPresent()) {
            throw new BaseException(BaseResponseStatus.EXIST_EMAIL);
        }
    }
}
