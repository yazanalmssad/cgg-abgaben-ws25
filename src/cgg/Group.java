package cgg;

import java.util.List;
import tools.BoundingBox;

public class Group implements Shape {
    private final List<Shape> shapes;
    private final BoundingBox boundingBox;

    public Group(List<Shape> shapes) {
        this.shapes = shapes;
        
        // BoundingBox der Gruppe berechnen
        if (shapes.isEmpty()) {
            this.boundingBox = BoundingBox.empty;
        } else {
            BoundingBox combinedBox = shapes.get(0).boundingBox();
            
            for (int i = 1; i < shapes.size(); i++) {
                combinedBox = combinedBox.extend(shapes.get(i).boundingBox());
            }
            
            this.boundingBox = combinedBox;
        }
    }

    @Override
    public BoundingBox boundingBox() {
        return boundingBox;
    }

    @Override
    public Hit intersect(Ray ray) {
        // BVH-Optimierung: Zuerst BoundingBox prüfen
        if (!boundingBox.intersect(ray.origin(), ray.direction(), ray.tmin(), ray.tmax())) {
            return null;
        }

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