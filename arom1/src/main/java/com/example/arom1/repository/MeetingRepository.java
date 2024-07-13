package com.example.arom1.repository;

import com.example.arom1.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    //제목으로 글 검색하기
    @Query("SELECT b FROM Meeting b WHERE b.title = :title")
    List<Meeting> findByTitle(@Param("title") String title);

    public List<Meeting> findByEateryId (Long eateryId);


}
