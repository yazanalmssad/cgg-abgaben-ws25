package cgg;

import tools.*;

public class WavefrontMaterial implements Material {
    private final Sampler baseColorSampler;
    private final Sampler specularColorSampler;
    private final double shininess;

    public WavefrontMaterial(Sampler baseColorSampler, Sampler specularColorSampler, double shininess) {
        this.baseColorSampler = baseColorSampler;
        this.specularColorSampler = specularColorSampler;
        this.shininess = shininess;
    }

    @Override
    public Color baseColor(Vec2 uv) {
        return baseColorSampler.getColor(uv);
    }

    @Override
    public Color specularColor(Vec2 uv) {
        return specularColorSampler.getColor(uv);
    }

    @Override
    public double shininess(Vec2 uv) {
        return shininess;
    }

    @Override
    public double reflectivity(Vec2 uv) {
        return 0.0;
    }

    @Override
    public Scatter scatter(Ray ray, Hit hit) {
        return null;
    }
}
