package com.example.arom1.repository;

import com.example.arom1.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EateryRepository extends JpaRepository<Eatery, Long> {
    List<Eatery> findByNameContaining(String keyword);
    List<Eatery> findBySiteWhlAddrContaining(String keyword);
    Optional<Eatery> findByName(String name);
    List<Eatery> findByUptaeNm(String category);
}