package cgg;

import tools.*;
import static tools.Functions.*;

public class Sphere {
    private final Vec3 center;
    private final double radius;
    private final Color color;

    public Sphere(Vec3 center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public Hit intersect(Ray ray) {
        Vec3 oc = subtract(ray.origin(), center); // Statische Funktion!

        double a = dot(ray.direction(), ray.direction());
        double b = 2.0 * dot(oc, ray.direction());
        double c = dot(oc, oc) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0)
            return null;

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);

        double t = Double.POSITIVE_INFINITY;
        if (ray.isValid(t1) && t1 < t)
            t = t1;
        if (ray.isValid(t2) && t2 < t)
            t = t2;
        if (t == Double.POSITIVE_INFINITY)
            return null;

        Vec3 point = ray.pointAt(t);

        Vec3 normal = normalize(subtract(point, center)); // Statische Funktionen!

        return new Hit(t, point, normal, color);
    }
}