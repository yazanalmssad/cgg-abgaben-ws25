package cgg;

import tools.*;
import static tools.Functions.*;

public class TexturedPhongMaterial implements Material {
    private final Sampler baseColorSampler;
    private final Color specularColor;
    private final double shininess;
    
    public TexturedPhongMaterial(Sampler baseColorSampler, Color specularColor, double shininess) {
        this.baseColorSampler = baseColorSampler;
        this.specularColor = specularColor;
        this.shininess = shininess;
    }
    
    @Override
    public Color baseColor(Vec2 uv) {
        return baseColorSampler.getColor(uv);
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
        return 0.0;
    }
}