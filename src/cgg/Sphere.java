/*package cgg;

import tools.*;
import static tools.Functions.*;

public class Sphere implements Shape {
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
}*/

package cgg;

import tools.*;
import static tools.Functions.*;

public class Sphere implements Shape {
    private final Vec3 center;
    private final double radius;
    private final Material material;

    public Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Sphere(Vec3 center, double radius, Color color) {
        this(center, radius, new Phong(color, Color.white, 10.0));
    }

    public Hit intersect(Ray ray) {
        Vec3 oc = subtract(ray.origin(), center);

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
        Vec3 normal = normalize(subtract(point, center));

        double phi = Math.atan2(normal.z(), normal.x());
        double theta = Math.acos(normal.y());

        double u = 1.0 - (phi + Math.PI) / (2.0 * Math.PI);
        double v = theta / Math.PI;

        Vec2 uv = vec2(u, v);

        return new Hit(t, point, normal, material, uv);
    }
}