package com.htsc.core_utils.utils;

import java.util.List;

public class GeoUtils {

  public static final float RADIUS = 6371e3f;

  /**
   * Compute distance between two geo-points.
   * <p>
   * Refer: <a
   * href="https://www.movable-type.co.uk/scripts/latlong.html#distance">Haversine
   * formula</a>
   *
   * @param p1 point 1 [x, y]
   * @param p2 point 2 [x, y]
   * @return distance
   */
  public static double distance(List<Double> p1, List<Double> p2) {
    double φ1 = p1.get(1) * Math.PI / 180; // φ, λ in radians
    double φ2 = p2.get(1) * Math.PI / 180;
    double Δφ = (p2.get(1) - p1.get(1)) * Math.PI / 180;
    double Δλ = (p2.get(0) - p1.get(0)) * Math.PI / 180;

    double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
        Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return RADIUS * c; // in metres
  }
}
