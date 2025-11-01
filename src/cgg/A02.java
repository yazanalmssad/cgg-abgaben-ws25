package cgg;

import java.util.*;
import tools.*;
import static tools.Functions.*;

public class A02 {
    public static void main(String[] args) {
        int width = 400;
        int height = 400;

        // Kamera erstellen
        var camera = new SimpleCamera(70, width, height);

        // Test erstellen
        List<Sphere> spheres = createTestSpheres();
        var tracer = new RayTracer(camera, spheres);

        var image = new Image(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, tracer.getColor(vec2(x, y)));
            }
        }

        image.writePng("a02");
    }

    private static List<Sphere> createTestSpheres() {
        List<Sphere> spheres = new ArrayList<>();

        // Kugeln
        spheres.add(new Sphere(vec3(0, 0, -3), 0.5, Color.gray)); // m
        spheres.add(new Sphere(vec3(2, 0, -4), 0.7, Color.green)); // r
        spheres.add(new Sphere(vec3(-2, 0, -4), 0.7, Color.blue)); // L
        spheres.add(new Sphere(vec3(-2, 2, -5), 0.8, Color.red)); // L
        spheres.add(new Sphere(vec3(-2, -2, -5), 0.8, Color.red)); // L
        spheres.add(new Sphere(vec3(0, 2, -4), 0.7, Color.cyan)); // m
        spheres.add(new Sphere(vec3(0, -2, -4), 0.7, Color.magenta)); // m
        spheres.add(new Sphere(vec3(2, 2, -5), 0.8, Color.red)); // r
        spheres.add(new Sphere(vec3(2, -2, -5), 0.8, Color.red)); // r

        spheres.add(new Sphere(vec3(0, 0, -3), 1.0, color(0.8, 0.8, 0.8)));
        //spheres.add(new Sphere(vec3(-2, 1, -5), 0.8, color(0.2, 0.6, 0.9)));
        spheres.add(new Sphere(vec3(1.5, -0.5, -6), 1.2, color(0.9, 0.3, 0.2)));
        spheres.add(new Sphere(vec3(0, -1000, 0), 998, color(0.4, 0.4, 0.4))); // Boden

        return spheres;
    }
}