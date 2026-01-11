package cgg;

import tools.*;
import static tools.Functions.*;

public class Metal implements Material {
    private static final double EPSILON = 1e-5;

    private final Color reflectionColor;
    private final double shininess;

    public Metal(Color reflectionColor) {
        this(reflectionColor, 200.0);
    }

    public Metal(Color reflectionColor, double shininess) {
        this.reflectionColor = reflectionColor;
        this.shininess = shininess;
    }

    @Override
    public Color baseColor(Vec2 uv) {
        return Color.black;
    }

    @Override
    public Color specularColor(Vec2 uv) {
        return reflectionColor;
    }

    @Override
    public double shininess(Vec2 uv) {
        return shininess;
    }

    @Override
    public double reflectivity(Vec2 uv) {
        return 1.0;
    }

    @Override
    public Scatter scatter(Ray ray, Hit hit) {
        Vec3 reflected = Optics.reflect(ray.direction(), hit.normal());
        Vec3 origin = add(hit.point(), multiply(reflected, EPSILON));
        return new Scatter(new Ray(origin, reflected), reflectionColor);
    }
}
