package cgg;

import tools.*;

public class Phong implements Material {
    private final Color baseColor;
    private final Color specularColor;
    private final double shininess;
    
    public Phong(Color baseColor, Color specularColor, double shininess) {
        this.baseColor = baseColor;
        this.specularColor = specularColor;
        this.shininess = shininess;
    }
    
    @Override
    public Color baseColor(Vec2 uv) {
        return baseColor;
    }
    
    @Override
    public Color specularColor(Vec2 uv) {
        return specularColor;
    }
    
    @Override
    public double shininess(Vec2 uv) {
        return shininess;
    }
    
    @Override
    public double reflectivity(Vec2 uv) {
        return 0.0; // Keine Reflexion in Ihrer Version
    }
}