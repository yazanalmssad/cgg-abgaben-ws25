/*package cgg;

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
}*/
package cgg;

import tools.*;
import static tools.Functions.*;

public class SimpleCamera {
    private final double fov;
    private final int width;
    private final int height;
    private final Vec3 position;
    private final Vec3 direction;

    public SimpleCamera(double fov, int width, int height) {
        this(fov, width, height, vec3(0, 0, 0), vec3(0, 0, -1));
    }

    public SimpleCamera(double fov, int width, int height, Vec3 position, Vec3 lookAt) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.position = position;
        this.direction = normalize(subtract(lookAt, position));
    }

    public Ray generateRay(Vec2 pixel) {
        double aspectRatio = (double) width / height;
        double x = (2.0 * pixel.x() / width - 1.0) * Math.tan(fov / 2.0) * aspectRatio;
        double y = (1.0 - 2.0 * pixel.y() / height) * Math.tan(fov / 2.0);

        Vec3 up = vec3(0, 1, 0);
        Vec3 right = normalize(cross(direction, up));
        Vec3 actualUp = normalize(cross(right, direction));

        Vec3 rayDirection = normalize(
                add(
                        multiply(right, x),
                        add(
                                multiply(actualUp, y),
                                direction)));

        return new Ray(position, rayDirection);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}