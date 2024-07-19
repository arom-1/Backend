package com.example.arom1.common;

import org.locationtech.jts.geom.Point;

public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371.01; // Earth's radius in kilometers

    public static double calculateDistance(Point point1, Point point2) {
        // Extract latitude and longitude from points
        double startLat = point1.getY();
        double startLon = point1.getX();
        double endLat = point2.getY();
        double endLon = point2.getX();

        // Convert latitude and longitude from degrees to radians
        double startLatRad = Math.toRadians(startLat);
        double startLonRad = Math.toRadians(startLon);
        double endLatRad = Math.toRadians(endLat);
        double endLonRad = Math.toRadians(endLon);

        // Haversine formula
        double deltaLat = endLatRad - startLatRad;
        double deltaLon = endLonRad - startLonRad;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(startLatRad) * Math.cos(endLatRad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS * c;
    }

}
