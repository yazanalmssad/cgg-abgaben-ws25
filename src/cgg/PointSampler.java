package cgg;

import tools.*;
import static tools.Functions.*;

public class PointSampler implements Sampler {
    private final Sampler baseSampler;

    public PointSampler(Sampler baseSampler) {
        this.baseSampler = baseSampler;
    }

    @Override
    public Color getColor(Vec2 pixel) {
        return baseSampler.getColor(vec2(pixel.x() + 0.5, pixel.y() + 0.5));
    }
}
