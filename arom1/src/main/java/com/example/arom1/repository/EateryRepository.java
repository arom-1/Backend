package com.example.arom1.repository;

import com.example.arom1.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EateryRepository extends JpaRepository<Eatery, Long> {
    List<Eatery> findByNameContaining(String keyword);
    List<Eatery> findBySiteWhlAddrContaining(String keyword);
    Optional<Eatery> findByName(String name);
    List<Eatery> findByUptaeNm(String category);

    @Query(value = "SELECT *, " +
            "(ST_Distance_Sphere(point(x, y), point(:x, :y))) as distance " +
            "FROM eatery " +
            "ORDER BY distance ASC " +
            "LIMIT 10", nativeQuery = true)
    List<Eatery> findNearbyEateries(@Param("x") double x, @Param("y") double y);
}