package com.example.arom1.repository;

import com.example.arom1.entity.EateryLocation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EateryLocationRepository extends JpaRepository<EateryLocation, Long> {
    @Query("SELECT l FROM EateryLocation l WHERE FUNCTION('ST_Distance_Sphere', l.point, :point) <= :distance * 1000")
    List<EateryLocation> findLocationsWithinDistance(@Param("point") Point point, @Param("distance") Integer distance);
}