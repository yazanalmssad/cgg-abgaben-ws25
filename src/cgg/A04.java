package cgg;

import java.util.*;
import tools.*;
import static tools.Functions.*;

public class A04 {
    public static void main(String[] args) {
        int width = 400;
        int height = 400;

        var camera = new SimpleCamera(70, width, height, vec3(0, 8, 4), vec3(0, 0, -4));

        List<Sphere> spheres = createOptimalSpheres();
        List<LightSource> lights = createOptimalLights();

        var tracer = new RayTracer(camera, spheres, lights);
        var image = new Image(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, tracer.getColor(vec2(x, y)));
            }
        }

        image.writePng("a04");
    }

    private static List<Sphere> createOptimalSpheres() {
        List<Sphere> spheres = new ArrayList<>();
        spheres.add(new Sphere(vec3(0, 0, -3), 1.0, color(1.0, 1.0, 1.0)));
        spheres.add(new Sphere(vec3(2, 0, -4), 0.7, Color.green)); // r
        spheres.add(new Sphere(vec3(-2, 0, -4), 0.7, Color.blue));
        spheres.add(new Sphere(vec3(-2, 2, -5), 0.8, Color.red)); // L
        spheres.add(new Sphere(vec3(-2, -2, -5), 0.8, Color.red)); // L
        spheres.add(new Sphere(vec3(0, 2, -4), 0.7, Color.cyan)); // m
        spheres.add(new Sphere(vec3(0, -2, -4), 0.7, Color.magenta)); // m
        spheres.add(new Sphere(vec3(2, 2, -5), 0.8, Color.red)); // r
        spheres.add(new Sphere(vec3(2, -2, -5), 0.8, Color.red)); // r
        spheres.add(new Sphere(vec3(0, 0, -3), 1.0, color(0.8, 0.8, 0.8)));
        spheres.add(new Sphere(vec3(0, -1000, 0), 998, color(0.4, 0.4, 0.4))); // Boden

        return spheres;
    }

    private static List<LightSource> createOptimalLights() {
        List<LightSource> lights = new ArrayList<>();

        // Licht 1: Rotes Licht von links
        lights.add(new DirectionalLight(normalize(vec3(-3, 8, 5)), color(2.0, 0, 0)));

        // Licht 2: Weißes Licht von rechts
        lights.add(new DirectionalLight(normalize(vec3(3, 2.2, 2)), color(1.0, 1.0, 1.0)));

        return lights;
    }
}