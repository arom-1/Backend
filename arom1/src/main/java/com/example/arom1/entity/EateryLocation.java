package com.example.arom1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Getter
@Entity
@Table(name = "eatery_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EateryLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @Column(name = "eatery_location", columnDefinition = "POINT SRID 4326")
    private Point point;

    @OneToOne(mappedBy = "eateryLocation")
    private Eatery eatery;

    @Builder
    public EateryLocation(Double longitude, Double latitude)  {
        this.point = newEateryLocation(latitude, longitude);
    }
    public static Point newEateryLocation(Double longitude, Double latitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
