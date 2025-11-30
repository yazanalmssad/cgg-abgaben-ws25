/*package cgg;

import cgg.LightSource.LightInfo;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public final class RayTracer implements Sampler {

    private final SimpleCamera camera;
    private final List<Sphere> spheres;
    private final List<LightSource> lights;
    private static final double EPSILON = 1e-5;

    public RayTracer(SimpleCamera camera, List<Sphere> spheres, List<LightSource> lights) {
        this.camera = camera;
        this.spheres = spheres;
        this.lights = lights;
    }

    public RayTracer(SimpleCamera camera, List<Sphere> spheres) {
        this(camera, spheres, List.of(
            new DirectionalLight(vec3(1, 1, 0.7), Color.white)
        ));
    }

    @Override
    public Color getColor(Vec2 pixel) {
        Ray ray = camera.generateRay(pixel);
        Hit hit = findClosestHit(ray);
        
        if (hit == null) {
            return Color.black;
        }
        
        return shade(hit);
    }

    private Hit findClosestHit(Ray ray) {
        Hit closestHit = null;
        double closestT = Double.POSITIVE_INFINITY;

        for (Sphere sphere : spheres) {
            Hit hit = sphere.intersect(ray);
            if (hit != null && hit.t() < closestT) {
                closestT = hit.t();
                closestHit = hit;
            }
        }
        return closestHit;
    }

    private Color shade(Hit hit) {
       
        Color C = hit.color();
        Color k_a = multiply(0.1, C);
        Color k_d = multiply(0.6, C);
        Color k_s = color(0.3, 0.3, 0.3);
        double k_e = 100;
        
        Color result = k_a;
        
        Vec3 viewDir = normalize(subtract(hit.point(), vec3(0,0,0)));
        for (LightSource light : lights) {
            LightInfo lightInfo = light.info(hit.point());
            Vec3 lightDir = lightInfo.direction();
            Color lightIntensity = lightInfo.intensity();
            double maxDistance = lightInfo.maxDistance();
            
            if (isVisible(hit.point(), lightDir, maxDistance)) {
                double diffuseFactor = Math.max(0.0, dot(hit.normal(), lightDir));
                
                if (diffuseFactor > 0) {
                    Color diffuse = multiply(diffuseFactor, multiply(k_d, lightIntensity));
                    
                    // Spiegelungsberechnung
                    Vec3 reflectDir = reflect(negate(lightDir), hit.normal());
                    double specularFactor = Math.pow(Math.max(0.0, dot(reflectDir, viewDir)), k_e);
                    Color specular = multiply(specularFactor, multiply(k_s, lightIntensity));
                    
                    result = add(result, add(diffuse, specular));
                }
            }
        }
        
        return result;
    }

    private boolean isVisible(Vec3 point, Vec3 lightDir, double maxDistance) {
        Vec3 offsetPoint = add(point, multiply(lightDir, EPSILON));
        Ray shadowRay = new Ray(offsetPoint, lightDir, EPSILON, maxDistance - EPSILON);
        
        for (Sphere sphere : spheres) {
            Hit hit = sphere.intersect(shadowRay);
            if (hit != null) {
                return false;
            }
        }
        return true;
    }

    // Spiegelungsberechnung: r = -s + 2(s·n)n
    private Vec3 reflect(Vec3 s, Vec3 n) {
        return subtract(multiply(n, 2 * dot(s, n)), s);
    }
}*/
package cgg;

import cgg.LightSource.LightInfo;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public final class RayTracer implements Sampler {

    private final SimpleCamera camera;
    private final List<? extends Shape> shapes;
    private final List<LightSource> lights;
    private static final double EPSILON = 1e-5;

    public RayTracer(SimpleCamera camera, List<? extends Shape> shapes, List<LightSource> lights) {
        this.camera = camera;
        this.shapes = shapes;
        this.lights = lights;
    }

    @Override
    public Color getColor(Vec2 pixel) {
        Ray ray = camera.generateRay(pixel);
        Hit hit = findClosestHit(ray);

        if (hit == null) {
            return Color.black;
        }

        return shade(hit);
    }

    private Hit findClosestHit(Ray ray) {
        Hit closestHit = null;
        double closestT = Double.POSITIVE_INFINITY;

        for (Shape shape : shapes) {
            Hit hit = shape.intersect(ray);
            if (hit != null && hit.t() < closestT) {
                closestT = hit.t();
                closestHit = hit;
            }
        }
        return closestHit;
    }

    private Color shade(Hit hit) {
        Color C = hit.color();
        Color k_a = multiply(0.1, C);
        Color k_d = multiply(0.6, C);
        Color k_s = color(1.0, 1.0, 1.0);
        double k_e = 50.0;

        Color result = k_a;

        Vec3 viewDir = normalize(negate(hit.point()));

        for (LightSource light : lights) {
            LightInfo lightInfo = light.info(hit.point());
            Vec3 lightDir = lightInfo.direction();
            Color lightIntensity = lightInfo.intensity();
            double maxDistance = lightInfo.maxDistance();

            if (isVisible(hit.point(), lightDir, maxDistance)) {
                double diffuseFactor = Math.max(0.0, dot(hit.normal(), lightDir));

                if (diffuseFactor > 0) {
                    Color diffuse = multiply(diffuseFactor, multiply(k_d, lightIntensity));

                    Vec3 reflectDir = reflect(negate(lightDir), hit.normal());
                    double specularFactor = Math.pow(Math.max(0.0, dot(reflectDir, viewDir)), k_e);
                    Color specular = multiply(specularFactor, multiply(k_s, lightIntensity));

                    result = add(result, add(diffuse, specular));
                }
            }
        }

        return result;
    }

    private boolean isVisible(Vec3 point, Vec3 lightDir, double maxDistance) {
        Vec3 offsetPoint = add(point, multiply(lightDir, EPSILON));
        Ray shadowRay = new Ray(offsetPoint, lightDir, EPSILON, maxDistance - EPSILON);

        for (Shape shape : shapes) {
            Hit hit = shape.intersect(shadowRay);
            if (hit != null) {
                return false;
            }
        }
        return true;
    }

    private Vec3 reflect(Vec3 s, Vec3 n) {
        return subtract(multiply(n, 2 * dot(s, n)), s);
    }
}