package com.example.arom1.repository;

import com.example.arom1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    //Id로 멤버 찾기
    @NonNull
    Optional<Member> findById(@NonNull Long id);

}
