package cgg;

import tools.*;

public class Hit {
    private final double t; // Strahlenparameter des Treffers
    private final Vec3 point; // Treffpunkt im 3D-Raum
    private final Vec3 normal; // Normale am Treffpunkt
    private final Color color; // Farbe des getroffenen Objekts

    public Hit(double t, Vec3 point, Vec3 normal, Color color) {
        this.t = t;
        this.point = point;
        this.normal = normal;
        this.color = color;
    }

    public double t() {
        return t;
    }

    public Vec3 point() {
        return point;
    }

    public Vec3 normal() {
        return normal;
    }

    public Color color() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("Hit[t=%.1f, point=%s, normal=%s]", t, point, normal);
    }
}