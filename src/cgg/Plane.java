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
    private final BoundingBox boundingBox;

    public Plane(Type type, double size, Color color) {
        this.type = type;
        this.size = size;
        this.color = color;
        
        if (type == Type.UNBOUNDED) {
            this.boundingBox = BoundingBox.everything;
        } else if (type == Type.CIRCULAR) {
            Vec3 min = vec3(-size, -0.001, -size);
            Vec3 max = vec3(size, 0.001, size);
            this.boundingBox = new BoundingBox(min, max);
        } else { // SQUARE
            double halfSize = size / 2;
            Vec3 min = vec3(-halfSize, -0.001, -halfSize);
            Vec3 max = vec3(halfSize, 0.001, halfSize);
            this.boundingBox = new BoundingBox(min, max);
        }
    }

    public Plane(Color color) {
        this(Type.UNBOUNDED, 0, color);
    }

    @Override
    public BoundingBox boundingBox() {
        return boundingBox;
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