package cgg;

import tools.*;
import static tools.Functions.*;

public class Triangle implements Shape {
    private final Vertex v0;
    private final Vertex v1;
    private final Vertex v2;
    private final Material material;
    private final BoundingBox boundingBox;
    private final Vec3 faceNormal;

    public Triangle(Vertex v0, Vertex v1, Vertex v2, Material material) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.material = material;

        Vec3 p0 = v0.position();
        Vec3 p1 = v1.position();
        Vec3 p2 = v2.position();

        this.boundingBox = BoundingBox.around(p0, p1, p2);
        this.faceNormal = normalize(cross(subtract(p1, p0), subtract(p2, p0)));
    }

    public Triangle(Vertex v0, Vertex v1, Vertex v2, Color color) {
        this(v0, v1, v2, new Phong(color, Color.white, 10.0));
    }

    @Override
    public BoundingBox boundingBox() {
        return boundingBox;
    }

    public Vec3 centroid() {
        return divide(add(v0.position(), v1.position(), v2.position()), 3.0);
    }

    @Override
    public Hit intersect(Ray ray) {
        Vec3 p0 = v0.position();
        Vec3 p1 = v1.position();
        Vec3 p2 = v2.position();

        Vec3 edge1 = subtract(p1, p0);
        Vec3 edge2 = subtract(p2, p0);

        Vec3 pvec = cross(ray.direction(), edge2);
        double det = dot(edge1, pvec);

        if (Math.abs(det) < EPSILON) {
            return null;
        }

        double invDet = 1.0 / det;
        Vec3 tvec = subtract(ray.origin(), p0);
        double u = dot(tvec, pvec) * invDet;
        if (u < 0.0 || u > 1.0) {
            return null;
        }

        Vec3 qvec = cross(tvec, edge1);
        double v = dot(ray.direction(), qvec) * invDet;
        if (v < 0.0 || u + v > 1.0) {
            return null;
        }

        double t = dot(edge2, qvec) * invDet;
        if (!ray.isValid(t)) {
            return null;
        }

        double w = 1.0 - u - v;
        Vec3 barycentric = vec3(w, u, v);

        Vec3 point = ray.pointAt(t);
        Vec3 normal = interpolate(v0.normal(), v1.normal(), v2.normal(), barycentric);
        if (squaredLength(normal) <= EPSILON) {
            normal = faceNormal;
        } else {
            normal = normalize(normal);
        }
        if (dot(normal, ray.direction()) > 0.0) {
            normal = negate(normal);
        }

        Vec2 uv = interpolate(v0.uv(), v1.uv(), v2.uv(), barycentric);

        return new Hit(t, point, normal, material, uv);
    }
}
