package cgg;

import java.util.Arrays;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public class A08 {
    public static void main(String[] args) {
        int width = 900;
        int height = 600;

        SimpleCamera camera = new SimpleCamera(
            Math.toRadians(45),
            width,
            height,
            vec3(0.4, 1.1, 2.8),
            vec3(0.0, 0.7, -5.2)
        );

        Sampler checker = new TransformSampler(new ImageTexture("images/checker.png"))
            .withScale(6.0, 6.0);
        Material ground = new TexturedPhongMaterial(checker, Color.white, 50.0);
        Material snow = new Phong(color(0.95, 0.96, 0.98), Color.white, 80.0);
        Material coal = new Phong(color(0.05, 0.05, 0.05), Color.white, 5.0);
        Material carrot = new Phong(color(0.95, 0.45, 0.12), Color.white, 25.0);
        Material scarf = new Phong(color(0.82, 0.1, 0.15), Color.white, 60.0);
        Material moon = new TexturedPhongMaterial(
            new ImageTexture("images/moon.png"),
            Color.white,
            120.0
        );

        Shape floor = new Sphere(vec3(0, -1001.0, -5.5), 1000, ground);

        Shape snowBase = new Sphere(vec3(0.0, -0.6, -5.6), 0.9, snow);
        Shape snowMid = new Sphere(vec3(0.0, 0.4, -5.6), 0.65, snow);
        Shape snowHead = new Sphere(vec3(0.0, 1.3, -5.6), 0.45, snow);

        Shape leftEye = new Sphere(vec3(-0.12, 1.38, -5.15), 0.06, coal);
        Shape rightEye = new Sphere(vec3(0.12, 1.38, -5.15), 0.06, coal);
        Shape nose = new Sphere(vec3(0.0, 1.3, -5.05), 0.09, carrot);

        Shape button1 = new Sphere(vec3(0.0, 0.6, -5.0), 0.06, coal);
        Shape button2 = new Sphere(vec3(0.0, 0.35, -5.02), 0.06, coal);
        Shape button3 = new Sphere(vec3(0.0, 0.1, -5.04), 0.06, coal);

        Shape scarfLeft = new Sphere(vec3(-0.25, 0.95, -5.25), 0.18, scarf);
        Shape scarfRight = new Sphere(vec3(0.25, 0.95, -5.25), 0.18, scarf);
        Shape scarfFront = new Sphere(vec3(0.0, 0.92, -5.05), 0.2, scarf);

        Shape moonSphere = new Sphere(vec3(2.2, 2.2, -9.0), 0.7, moon);

        List<Shape> shapes = Arrays.asList(
            floor,
            snowBase,
            snowMid,
            snowHead,
            leftEye,
            rightEye,
            nose,
            button1,
            button2,
            button3,
            scarfLeft,
            scarfRight,
            scarfFront,
            moonSphere
        );

        LightSource key = new DirectionalLight(
            normalize(vec3(-0.6, 0.9, -0.2)),
            color(2.4, 2.2, 2.0)
        );
        LightSource fill = new DirectionalLight(
            normalize(vec3(0.5, 0.3, -1.0)),
            color(0.6, 0.7, 0.85)
        );
        LightSource rim = new DirectionalLight(
            normalize(vec3(0.2, 0.4, 1.0)),
            color(0.3, 0.35, 0.45)
        );
        LightSource sky = new DirectionalLight(
            normalize(vec3(0.0, -1.0, 0.0)),
            color(0.05, 0.05, 0.08)
        );

        List<LightSource> lights = Arrays.asList(key, fill, rim, sky);

        RayTracer raytracer = new RayTracer(camera, shapes, lights);

        Image pointImage = new Image(width, height);
        pointImage.sample(new PointSampler(raytracer));
        pointImage.writePng("a08-point");

        seed(8);
        Image stratifiedImage = new Image(width, height);
        stratifiedImage.sample(new StratifiedSampler(raytracer, 10));
        stratifiedImage.writePng("a08-stratified");
    }
}
