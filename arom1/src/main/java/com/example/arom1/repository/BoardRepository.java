package com.example.arom1.repository;

import com.example.arom1.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //제목으로 글 검색하기
    @Query("SELECT b FROM Board b WHERE b.title = :title")
    List<Board> findByTitle(@Param("title") String title);



}
