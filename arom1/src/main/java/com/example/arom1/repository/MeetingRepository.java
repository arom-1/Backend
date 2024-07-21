package com.example.arom1.repository;

import com.example.arom1.entity.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    //제목으로 글 검색하기
    List<Meeting> findByTitleContaining(String title);

    public List<Meeting> findByEateryName (String eateryName);

    public List<Meeting> findByEateryId(long eateryId);
}
