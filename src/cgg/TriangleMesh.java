package cgg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tools.*;

public class TriangleMesh implements Shape {
    private static final int LEAF_SIZE = 12;

    private final Node root;
    private final BoundingBox boundingBox;

    public TriangleMesh(List<Triangle> triangles) {
        if (triangles.isEmpty()) {
            this.root = null;
            this.boundingBox = BoundingBox.empty;
        } else {
            this.root = build(triangles);
            this.boundingBox = root.bounds;
        }
    }

    @Override
    public BoundingBox boundingBox() {
        return boundingBox;
    }

    @Override
    public Hit intersect(Ray ray) {
        if (root == null) {
            return null;
        }
        return intersectNode(root, ray, null);
    }

    private static class Node {
        private final BoundingBox bounds;
        private final List<Triangle> triangles;
        private final Node left;
        private final Node right;

        private Node(BoundingBox bounds, List<Triangle> triangles, Node left, Node right) {
            this.bounds = bounds;
            this.triangles = triangles;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return triangles != null;
        }
    }

    private Node build(List<Triangle> triangles) {
        BoundingBox bounds = BoundingBox.empty;
        for (Triangle t : triangles) {
            bounds = bounds.extend(t.boundingBox());
        }

        if (triangles.size() <= LEAF_SIZE) {
            return new Node(bounds, triangles, null, null);
        }

        Axis axis = bounds.longest();
        List<Triangle> sorted = new ArrayList<>(triangles);
        sorted.sort(Comparator.comparingDouble(t -> axisValue(t.centroid(), axis)));

        int mid = sorted.size() / 2;
        if (mid == 0 || mid == sorted.size()) {
            return new Node(bounds, sorted, null, null);
        }

        List<Triangle> leftList = new ArrayList<>(sorted.subList(0, mid));
        List<Triangle> rightList = new ArrayList<>(sorted.subList(mid, sorted.size()));

        Node left = build(leftList);
        Node right = build(rightList);
        return new Node(bounds, null, left, right);
    }

    private Hit intersectNode(Node node, Ray ray, Hit best) {
        double maxT = best != null ? best.t() : ray.tmax();
        if (!node.bounds.intersect(ray.origin(), ray.direction(), ray.tmin(), maxT)) {
            return best;
        }

        if (node.isLeaf()) {
            Hit closestHit = best;
            double closestT = maxT;
            for (Triangle triangle : node.triangles) {
                Hit hit = triangle.intersect(ray);
                if (hit != null && hit.t() < closestT) {
                    closestT = hit.t();
                    closestHit = hit;
                }
            }
            return closestHit;
        }

        Hit leftHit = intersectNode(node.left, ray, best);
        return intersectNode(node.right, ray, leftHit);
    }

    private static double axisValue(Vec3 v, Axis axis) {
        return switch (axis) {
            case X -> v.x();
            case Y -> v.y();
            case Z -> v.z();
        };
    }
}
