package cgg;

import tools.*;

public interface LightSource {
    public record LightInfo(Vec3 direction, Color intensity, double maxDistance) {}
    
    LightInfo info(Vec3 x);
}