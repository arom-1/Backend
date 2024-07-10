package com.example.arom1.repository;

import com.example.arom1.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EateryRepository extends JpaRepository<Eatery, Long> {
    Optional<Eatery> findByName(String keyword);
}