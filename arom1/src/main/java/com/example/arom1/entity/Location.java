
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "location")
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @Column(name = "location", columnDefinition = "POINT SRID 4326")
    private Point point;

    @OneToOne(mappedBy = "location")
    private Member member;

    @Builder
    public Location(Double latitude, Double longitude)  {
        this.point = newLocation(latitude, longitude);
    }
    public static Point newLocation(Double latitude, Double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public static Double getDistance(Point point1, Point point2) {
        double distance = point1.distance(point2);
        return distance / 1000;
    }

}
