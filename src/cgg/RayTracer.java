package cgg;

import java.util.List;
import tools.*;
import static tools.Functions.*;

public final class RayTracer implements Sampler {

    private final SimpleCamera camera;
    private final List<Sphere> spheres;

    public RayTracer(SimpleCamera camera, List<Sphere> spheres) {
        this.camera = camera;
        this.spheres = spheres;
    }

    @Override
    public Color getColor(Vec2 pixel) {
        // 1. Strahl erzeugen
        Ray ray = camera.generateRay(pixel);

        Hit closestHit = null;
        double closestT = Double.POSITIVE_INFINITY;

        for (Sphere s : spheres) {
            Hit h = s.intersect(ray);
            if (h != null && h.t() < closestT) {
                closestT = h.t();
                closestHit = h;
            }
        }

        if (closestHit == null) {
            // Background color
            //return color(0.7, 0.8, 1.0);
            return Color.black;
        }

        Vec3 lightDir = normalize(vec3(1, 1, 0.7));
        double diff = Math.max(0.0, dot(lightDir, closestHit.normal()));
        Color ambient = multiply(0.1, closestHit.color());
        Color diffuse = multiply(diff * 0.9, closestHit.color());
        return add(ambient, diffuse);

    }
}