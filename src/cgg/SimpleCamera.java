package cgg;

import tools.*;
import static tools.Functions.*;

public class SimpleCamera {
    private final double fov;
    private final int width;
    private final int height;

    public SimpleCamera(double fov, int width, int height) {
        this.fov = Math.toRadians(fov);
        this.width = width;
        this.height = height;
    }

    public Ray generateRay(Vec2 pixel) {
        double aspect = (double) width / height;
        double x = (2.0 * pixel.x() / width - 1.0) * Math.tan(fov / 2) * aspect;
        double y = (1.0 - 2.0 * pixel.y() / height) * Math.tan(fov / 2);

        Vec3 direction = normalize(vec3(x, y, -1)); // Statische Funktion!
        return new Ray(Vec3.zero, direction);
    }
}