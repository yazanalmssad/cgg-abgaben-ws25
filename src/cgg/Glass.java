package cgg;

import tools.*;
import static tools.Functions.*;

public class Glass implements Material {
    private static final double EPSILON = 1e-5;

    private final double opticalIndex;
    private final Color tint;

    public Glass(double opticalIndex) {
        this(opticalIndex, Color.white);
    }

    public Glass(double opticalIndex, Color tint) {
        this.opticalIndex = opticalIndex;
        this.tint = tint;
    }

    @Override
    public Color baseColor(Vec2 uv) {
        return Color.black;
    }

    @Override
    public Color specularColor(Vec2 uv) {
        return Color.white;
    }

    @Override
    public double shininess(Vec2 uv) {
        return 200.0;
    }

    @Override
    public double reflectivity(Vec2 uv) {
        return 1.0;
    }

    @Override
    public Scatter scatter(Ray ray, Hit hit) {
        Vec3 n = hit.normal();
        double n1 = 1.0;
        double n2 = opticalIndex;

        if (dot(ray.direction(), n) > 0) {
            n1 = opticalIndex;
            n2 = 1.0;
            n = negate(n);
        }

        Vec3 refracted = Optics.refract(ray.direction(), n, n1, n2);
        double reflectProb = Optics.schlick(ray.direction(), n, n1, n2);
        reflectProb = Math.max(0.0, Math.min(1.0, reflectProb));

        if (refracted == null || random() < reflectProb) {
            Vec3 reflected = Optics.reflect(ray.direction(), n);
            Vec3 origin = add(hit.point(), multiply(reflected, EPSILON));
            return new Scatter(new Ray(origin, reflected), tint);
        }

        Vec3 origin = add(hit.point(), multiply(refracted, EPSILON));
        return new Scatter(new Ray(origin, refracted), tint);
    }
}
