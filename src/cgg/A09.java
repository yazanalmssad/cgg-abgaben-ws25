package cgg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public class A09 {
    public static void main(String[] args) {
        int width = 900;
        int height = 600;

        SimpleCamera camera = new SimpleCamera(
            Math.toRadians(40),
            width,
            height,
            vec3(2.4, 1.6, 3.8),
            vec3(0.0, 0.8, -4.8)
        );

        Sampler checker = new TransformSampler(new ImageTexture("images/checker.png"))
            .withScale(4.0, 4.0);
        Material ground = new TexturedPhongMaterial(checker, Color.white, 40.0);

        Shape floor = new Sphere(vec3(0, -1001.0, -5.0), 1000, ground);
        Shape moon = new Sphere(
            vec3(2.2, 2.0, -9.5),
            1.0,
            new TexturedPhongMaterial(new ImageTexture("images/moon.png"), Color.white, 80.0)
        );

        List<Shape> shapes = new ArrayList<>();
        shapes.add(floor);
        shapes.add(moon);

        List<Wavefront.MeshData> meshes = Wavefront.loadMeshData("models/a09.obj");
        Vec3 translation = vec3(0.0, -0.1, -5.0);
        double scale = 1.25;
        Mat44 transform = multiply(
            move(translation),
            rotate(Vec3.yAxis, 25.0),
            rotate(Vec3.xAxis, -8.0),
            scale(scale, scale, scale)
        );

        for (Wavefront.MeshData mesh : meshes) {
            Material material = toMaterial(mesh.material());
            List<Triangle> triangles = new ArrayList<>(mesh.triangles().size());

            for (Wavefront.TriangleData tri : mesh.triangles()) {
                Vertex a = transformVertex(tri.v0(), transform);
                Vertex b = transformVertex(tri.v1(), transform);
                Vertex c = transformVertex(tri.v2(), transform);
                triangles.add(new Triangle(a, b, c, material));
            }

            shapes.add(new TriangleMesh(triangles));
        }

        LightSource key = new DirectionalLight(
            normalize(vec3(-0.7, 1.0, -0.3)),
            color(2.1, 2.0, 1.9)
        );
        LightSource fill = new DirectionalLight(
            normalize(vec3(0.6, 0.3, -1.0)),
            color(0.55, 0.65, 0.8)
        );
        LightSource rim = new DirectionalLight(
            normalize(vec3(0.2, 0.7, 1.0)),
            color(0.6, 0.65, 0.8)
        );

        List<LightSource> lights = Arrays.asList(key, fill, rim);

        RayTracer raytracer = new RayTracer(camera, shapes, lights);

        Image image = new Image(width, height);
        image.sample(new PointSampler(raytracer));
        image.writePng("a09");
    }

    private static Material toMaterial(Wavefront.MaterialData material) {
        return new WavefrontMaterial(
            material.kdMap(),
            material.ksMap(),
            material.ns()
        );
    }

    private static Vertex transformVertex(Vertex v, Mat44 transform) {
        Vec3 p = multiplyPoint(transform, v.position());
        Vec3 n = v.normal();
        if (squaredLength(n) > EPSILON) {
            n = normalize(multiplyDirection(transform, n));
        }
        return new Vertex(p, n, v.uv(), v.color());
    }
}
