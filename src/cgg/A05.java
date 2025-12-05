package cgg;

import java.util.Arrays;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public class A05 {
    public static void main(String[] args) {

        int width = 600;
        int height = 600;

        SimpleCamera camera = new SimpleCamera(70, width, height);

        Material earth = new TexturedPhongMaterial(
                new ImageTexture("images/earth.png"),
                Color.white,
                500.0);

        Material moon = new TexturedPhongMaterial(
                new ImageTexture("images/moon.png"),
                Color.white,
                200.0);

        Material marble = new TexturedPhongMaterial(
                new ImageTexture("images/marble.png"),
                Color.white,
                300.0);

        Material checker = new TexturedPhongMaterial(
                new ImageTexture("images/checker.png"),
                Color.white,
                100.0);

        Material floorMaterial = new Phong(color(0.7, 0.7, 0.7), Color.white, 10);
        Shape floor = new Sphere(vec3(0, -1001, -5), 1000, floorMaterial);

        Shape globe = new Sphere(vec3(0, 0, -5), 1.0, earth);
        Shape moonSphere = new Sphere(vec3(-2.0, 1, -4.5), 0.4, moon);
        Shape marbleSphere = new Sphere(vec3(1.6, 0.3, -4.0), 0.7, marble);
        Shape checkerSphere = new Sphere(vec3(0, -0.8, -3.0), 0.3, checker);

        List<Shape> shapes = Arrays.asList(
                floor, globe, moonSphere, marbleSphere, checkerSphere);

        LightSource sun = new DirectionalLight(vec3(1, 1, -0.3), color(3.0, 0.95, 0.9));
        LightSource point = new PointLight(vec3(2, 3, 1), color(1, 1, 1));

        List<LightSource> lights = Arrays.asList(sun, point);

        RayTracer raytracer = new RayTracer(camera, shapes, lights);

        Image image = new Image(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vec2 pixel = vec2(x, y);
                Color c = raytracer.getColor(pixel);
                image.setPixel(x, y, c);
            }
        }

        image.writePng("a05");
    }
} 
