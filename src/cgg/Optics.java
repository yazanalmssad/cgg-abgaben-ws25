package cgg;

import tools.Vec3;
import static tools.Functions.*;

public final class Optics {
    private Optics() {
    }

    public static Vec3 reflect(Vec3 d, Vec3 n) {
        return subtract(d, multiply(2.0 * dot(d, n), n));
    }

    public static double schlick(Vec3 d, Vec3 n, double n1, double n2) {
        double r0 = (n1 - n2) / (n1 + n2);
        r0 *= r0;
        double cos = -dot(d, n);
        return r0 + (1.0 - r0) * Math.pow(1.0 - cos, 5.0);
    }

    public static Vec3 refract(Vec3 d, Vec3 n, double n1, double n2) {
        double eta = n1 / n2;
        double cosi = dot(d, n);
        double k = 1.0 - eta * eta * (1.0 - cosi * cosi);
        if (k < 0.0) {
            return null;
        }
        return add(
            multiply(eta, d),
            multiply(-eta * cosi - Math.sqrt(k), n)
        );
    }
}
