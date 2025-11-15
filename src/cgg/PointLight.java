package cgg;

import tools.*;
import static tools.Functions.*;

public class PointLight implements LightSource {
    private final Vec3 position;
    private final Color intensity;
    
    public PointLight(Vec3 position, Color intensity) {
        this.position = position;
        this.intensity = intensity;
    }
    
    @Override
    public LightInfo info(Vec3 x) {
        Vec3 toLight = subtract(position, x);
        double distance = length(toLight);
        Vec3 direction = normalize(toLight);
        
        Color attenuatedIntensity = multiply(1.0 / (distance * distance), intensity);
        
        return new LightInfo(direction, attenuatedIntensity, distance);
    }
}