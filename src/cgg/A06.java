package cgg;

import java.util.Arrays;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public class A06 {
    public static void main(String[] args) {
        int width = 900;
        int height = 600;

        SimpleCamera camera = new SimpleCamera(
            Math.toRadians(50),
            width,
            height,
            vec3(0, 1.1, 3.8),
            vec3(0, 0.35, -4.2)
        );

        Material ground = new Phong(color(0.92, 0.92, 0.9), Color.white, 25.0);
        Material lime = new Phong(color(0.78, 0.9, 0.2), Color.white, 35.0);
        Material softGreen = new Phong(color(0.6, 0.82, 0.28), Color.white, 25.0);
        Material metal = new Metal(color(0.98, 0.98, 0.98));
        Material gold = new Metal(color(0.95, 0.82, 0.45));
        Material glass = new Glass(1.5, color(0.9, 0.98, 0.98));

        Shape floor = new Sphere(vec3(0, -1001.6, -4), 1000, ground);
        Shape limeSphere = new Sphere(vec3(-1.4, 0.7, -6.2), 1.2, lime);
        Shape softGreenSphere = new Sphere(vec3(1.2, 0.35, -5.0), 0.7, softGreen);
        Shape metalSphere = new Sphere(vec3(-0.2, 0.3, -3.3), 0.45, metal);
        Shape goldSphere = new Sphere(vec3(0.6, 0.15, -2.7), 0.25, gold);
        Shape glassSphere = new Sphere(vec3(1.6, 0.45, -3.9), 0.7, glass);

        List<Shape> shapes = Arrays.asList(
            floor,
            limeSphere,
            softGreenSphere,
            metalSphere,
            goldSphere,
            glassSphere
        );

        LightSource key = new DirectionalLight(
            normalize(vec3(0.6, 0.9, -0.2)),
            color(2.8, 2.6, 2.4)
        );
        LightSource fill = new DirectionalLight(
            normalize(vec3(-0.4, 0.5, -1.0)),
            color(0.6, 0.7, 0.9)
        );
        LightSource rim = new DirectionalLight(
            normalize(vec3(-0.7, 0.2, 1.0)),
            color(0.3, 0.35, 0.45)
        );
        LightSource overhead = new PointLight(
            vec3(0.0, 3.2, 1.6),
            color(0.8, 0.8, 0.8)
        );

        List<LightSource> lights = Arrays.asList(key, fill, rim, overhead);

        RayTracer raytracer = new RayTracer(camera, shapes, lights);
        Image image = new Image(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vec2 pixel = vec2(x + 0.5, y + 0.5);
                image.setPixel(x, y, raytracer.getColor(pixel));
            }
        }

        image.writePng("a06");
    }
}
