package cgg;

import tools.*;
import static tools.Functions.*;

public class TransformSampler implements Sampler {
    private final Sampler baseSampler;
    private final double offsetU;
    private final double offsetV;
    private final double scaleU;
    private final double scaleV;
    private final double rotation;

    public TransformSampler(Sampler baseSampler) {
        this(baseSampler, 0.0, 0.0, 1.0, 1.0, 0.0);
    }

    public TransformSampler(Sampler baseSampler, double offsetU, double offsetV,
            double scaleU, double scaleV, double rotation) {
        this.baseSampler = baseSampler;
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.rotation = rotation;
    }

    @Override
    public Color getColor(Vec2 uv) {
        double u = uv.u();
        double v = uv.v();

        double rad = Math.toRadians(rotation);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double rotatedU = u * cos - v * sin;
        double rotatedV = u * sin + v * cos;

        double scaledU = rotatedU * scaleU;
        double scaledV = rotatedV * scaleV;

        double transformedU = scaledU + offsetU;
        double transformedV = scaledV + offsetV;

        return baseSampler.getColor(vec2(transformedU, transformedV));
    }

    public TransformSampler withOffset(double offsetU, double offsetV) {
        return new TransformSampler(baseSampler, this.offsetU + offsetU,
                this.offsetV + offsetV, scaleU, scaleV, rotation);
    }

    public TransformSampler withScale(double scaleU, double scaleV) {
        return new TransformSampler(baseSampler, offsetU, offsetV,
                this.scaleU * scaleU, this.scaleV * scaleV, rotation);
    }

    public TransformSampler withRotation(double rotation) {
        return new TransformSampler(baseSampler, offsetU, offsetV,
                scaleU, scaleV, this.rotation + rotation);
    }
}