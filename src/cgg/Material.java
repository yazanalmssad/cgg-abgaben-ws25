package cgg;

import tools.*;

public interface Material {
    Color baseColor(Vec2 uv);
    Color specularColor(Vec2 uv);
    double shininess(Vec2 uv);
    double reflectivity(Vec2 uv);
}