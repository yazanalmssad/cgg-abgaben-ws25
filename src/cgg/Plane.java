package cgg;

import tools.*;
import static tools.Functions.*;

public class Plane implements Shape {
    public enum Type {
        UNBOUNDED, CIRCULAR, SQUARE
    }

    private final Type type;
    private final double size;
    private final Color color;

    public Plane(Type type, double size, Color color) {
        this.type = type;
        this.size = size;
        this.color = color;
    }

    public Plane(Color color) {
        this(Type.UNBOUNDED, 0, color);
    }

    @Override
    public Hit intersect(Ray ray) {

        if (Math.abs(ray.direction().y()) < 1e-6) {
            return null;
        }

        double t = -ray.origin().y() / ray.direction().y();
        if (t < ray.tmin() || t > ray.tmax()) {
            return null;
        }

        Vec3 point = ray.pointAt(t);

        if (!isWithinBounds(point)) {
            return null;
        }

        Vec3 normal = vec3(0, 1, 0);

        if (dot(normal, ray.direction()) > 0) {
            normal = negate(normal);
        }

        return new Hit(t, point, normal, color);
    }

    private boolean isWithinBounds(Vec3 point) {
        double x = point.x();
        double z = point.z();

        switch (type) {
            case CIRCULAR:
                return x * x + z * z <= size * size;
            case SQUARE:
                return Math.abs(x) <= size / 2 && Math.abs(z) <= size / 2;
            case UNBOUNDED:
            default:
                return true;
        }
    }
}