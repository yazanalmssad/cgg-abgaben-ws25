package cgg;

import tools.*;
import static tools.Functions.*;

public class Ray {
    private final Vec3 origin; // Startpunkt (0,0,0)
    private final Vec3 direction; // Normalisierte Richtung
    private final double tmin; 
    private final double tmax; 

    public Ray(Vec3 origin, Vec3 direction, double tmin, double tmax) {
        this.origin = origin;
        this.direction = normalize(direction); // Statische Funktion!
        this.tmin = tmin;
        this.tmax = tmax;
    }

    public Ray(Vec3 origin, Vec3 direction) {
        this(origin, direction, 0.0, Double.POSITIVE_INFINITY);
    }

    public Vec3 pointAt(double t) {
        return add(origin, multiply(direction, t)); // Statische Funktionen!
    }

    public boolean isValid(double t) {
        return t >= tmin && t <= tmax;
    }

    public Vec3 origin() {
        return origin;
    }

    public Vec3 direction() {
        return direction;
    }

    public double tmin() {
        return tmin;
    }

    public double tmax() {
        return tmax;
    }
}