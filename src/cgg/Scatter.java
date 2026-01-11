package cgg;

import tools.Color;

public class Scatter {
    private final Ray ray;
    private final Color attenuation;

    public Scatter(Ray ray, Color attenuation) {
        this.ray = ray;
        this.attenuation = attenuation;
    }

    public Ray ray() {
        return ray;
    }

    public Color attenuation() {
        return attenuation;
    }
}
