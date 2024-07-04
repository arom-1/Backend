package com.example.arom1.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_location")
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
    private Location(Point point) {
        this.point = point;
    }
    public static Location newLocation(Point point) {
        return Location.builder()
                .point(point)
                .build();
    }


}
