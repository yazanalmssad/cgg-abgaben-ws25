package cgg;

import tools.*;
import static tools.Functions.*;

public class DirectionalLight implements LightSource {
    private final Vec3 direction;
    private final Color intensity;
    
    public DirectionalLight(Vec3 direction, Color intensity) {
        this.direction = normalize(direction);
        this.intensity = intensity;
    }
    
    @Override
    public LightInfo info(Vec3 x) {
        return new LightInfo(direction, intensity, Double.POSITIVE_INFINITY);
    }
}