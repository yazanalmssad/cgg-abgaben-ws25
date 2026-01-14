package cgg;

import tools.*;
import static tools.Functions.*;

public class StratifiedSampler implements Sampler {
    private final Sampler baseSampler;
    private final int strata;
    private final double invStrata;
    private final double sampleCount;

    public StratifiedSampler(Sampler baseSampler, int strata) {
        if (strata <= 0) {
            throw new IllegalArgumentException("strata must be positive");
        }
        this.baseSampler = baseSampler;
        this.strata = strata;
        this.invStrata = 1.0 / strata;
        this.sampleCount = strata * strata;
    }

    @Override
    public Color getColor(Vec2 pixel) {
        Color sum = Color.black;
        for (int y = 0; y < strata; y++) {
            for (int x = 0; x < strata; x++) {
                double jitterX = random();
                double jitterY = random();
                double offsetX = (x + jitterX) * invStrata;
                double offsetY = (y + jitterY) * invStrata;
                Vec2 sample = vec2(pixel.x() + offsetX, pixel.y() + offsetY);
                sum = add(sum, baseSampler.getColor(sample));
            }
        }
        return divide(sum, sampleCount);
    }
}
