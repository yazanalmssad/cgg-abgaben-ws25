package cgg;

import tools.BoundingBox;

public interface Shape {
    Hit intersect(Ray ray);
    BoundingBox boundingBox();
}
