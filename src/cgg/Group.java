package cgg;

import java.util.List;

public class Group implements Shape {
    private final List<Shape> shapes;

    public Group(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null;
        double closestT = Double.POSITIVE_INFINITY;

        for (Shape shape : shapes) {
            Hit hit = shape.intersect(ray);
            if (hit != null && hit.t() < closestT) {
                closestT = hit.t();
                closestHit = hit;
            }
        }
        return closestHit;
    }
}