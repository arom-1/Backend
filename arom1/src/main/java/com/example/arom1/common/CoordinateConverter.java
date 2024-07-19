package com.example.arom1.common;

import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

@Component
public class CoordinateConverter {
    public ProjCoordinate transform(Double strLon, Double strLat){
        CRSFactory crsFactory = new CRSFactory();
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        // EPSG:2097 (중부원점TM) 좌표계 정의
        CoordinateReferenceSystem crs2097 = crsFactory.createFromName("EPSG:2097");

        // EPSG:4326 (경도/위도) 좌표계 정의
        CoordinateReferenceSystem crs4326 = crsFactory.createFromName("EPSG:4326");
        CoordinateTransform transform = ctFactory.createTransform(crs2097, crs4326);

        ProjCoordinate sourceCoordinate = new ProjCoordinate(strLon, strLat);
        // 결과 좌표 (EPSG:4326)
        ProjCoordinate targetCoordinate = new ProjCoordinate();
        // 좌표 변환 수행
        transform.transform(sourceCoordinate, targetCoordinate);
        return targetCoordinate;
    }
}