package cgg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tools.*;
import static tools.Functions.*;

public class SceneGenerator {
    
    public static List<Shape> createSolarSystemScene() {
        List<Shape> shapes = new ArrayList<>();
        Random rand = new Random(42);
        
        // SONNE - Leuchtend und imposant
        Material sunMaterial = new Phong(
            color(1.0, 0.95, 0.7),  // Warmes gelb-weißes Licht
            color(1.0, 1.0, 0.9),   // Helle Highlights
            200.0                   // Sehr glänzend
        );
        shapes.add(new Sphere(vec3(0, 0, 0), 3.5, sunMaterial));
        
        // Sonnen-Korona-Effekt (leuchtende Partikel um die Sonne)
        for (int i = 0; i < 100; i++) {
            double angle = rand.nextDouble() * Math.PI * 2;
            double distance = 4.0 + rand.nextDouble() * 1.5;
            double height = (rand.nextDouble() - 0.5) * 2.0;
            
            Vec3 pos = vec3(
                Math.cos(angle) * distance,
                height,
                Math.sin(angle) * distance
            );
            
            Color coronaColor = color(
                1.0,
                0.8 + rand.nextDouble() * 0.2,
                0.5 + rand.nextDouble() * 0.3
            );
            
            shapes.add(new Sphere(pos, 0.05 + rand.nextDouble() * 0.1,
                new Phong(coronaColor, Color.white, 100.0)));
        }
        
        // MERKUR - Grau und zerklüftet
        createPlanet(shapes, vec3(8, 0, 0), 0.4, 
            color(0.5, 0.5, 0.5), 0, rand, "Merkur");
        
        // VENUS - Hellgelb mit dichten Wolken
        Material venusMaterial = new Phong(
            color(0.95, 0.85, 0.6),
            color(0.9, 0.8, 0.5),
            40.0
        );
        shapes.add(new Sphere(vec3(12, 0, 0), 0.6, venusMaterial));
        
        // ERDE - Blau mit Kontinenten-Andeutung
        createEarth(shapes, vec3(16, 0, 0), 0.65, rand);
        
        // MARS - Rostrot mit Polkappen
        createMars(shapes, vec3(21, 0, 0), 0.5, rand);
        
        // ASTEROIDENGÜRTEL zwischen Mars und Jupiter
        createAsteroidBelt(shapes, 26, 28, 500, rand);
        
        // JUPITER - Massiver Gasriese mit Großem Roten Fleck
        createJupiter(shapes, vec3(35, 0, 0), 2.2, rand);
        
        // SATURN - Mit spektakulären Ringen
        createSaturn(shapes, vec3(48, 0, 0), 1.8, rand);
        
        // URANUS - Eisblau geneigt
        createUranus(shapes, vec3(60, 0, 0), 1.2, rand);
        
        // NEPTUN - Tiefblau mit Stürmen
        createNeptune(shapes, vec3(72, 0, 0), 1.15, rand);
        
        // KUIPER-GÜRTEL Objekte (jenseits von Neptun)
        for (int i = 0; i < 200; i++) {
            double distance = 80 + rand.nextDouble() * 15;
            double angle = rand.nextDouble() * Math.PI * 2;
            double height = (rand.nextDouble() - 0.5) * 8;
            
            Vec3 pos = vec3(
                Math.cos(angle) * distance,
                height,
                Math.sin(angle) * distance
            );
            
            shapes.add(new Sphere(pos, 0.1 + rand.nextDouble() * 0.3,
                new Phong(color(0.6, 0.6, 0.7), Color.white, 20.0)));
        }
        
        // FERNSTERNE - Mehrfarbig und unterschiedlich hell
        for (int i = 0; i < 3000; i++) {
            double distance = 120 + rand.nextDouble() * 80;
            double angle = rand.nextDouble() * Math.PI * 2;
            double height = (rand.nextDouble() - 0.5) * 60;
            
            double x = Math.cos(angle) * distance;
            double y = height;
            double z = Math.sin(angle) * distance;
            
            // Verschiedene Sterntypen (rot, gelb, blau, weiß)
            Color starColor;
            double starType = rand.nextDouble();
            if (starType < 0.1) {
                // Rote Riesen
                starColor = color(1.0, 0.4, 0.3);
            } else if (starType < 0.3) {
                // Blaue Riesen
                starColor = color(0.7, 0.8, 1.0);
            } else if (starType < 0.5) {
                // Gelbe Sterne
                starColor = color(1.0, 1.0, 0.7);
            } else {
                // Weiße Sterne
                starColor = color(0.9, 0.9, 1.0);
            }
            
            double brightness = 0.5 + rand.nextDouble() * 0.5;
            starColor = multiply(brightness, starColor);
            
            shapes.add(new Sphere(
                vec3(x, y, z),
                0.08 + rand.nextDouble() * 0.15,
                new Phong(starColor, starColor, 80.0)
            ));
        }
        
        System.out.println("Gesamtzahl der Objekte: " + shapes.size());
        return shapes;
    }
    
    private static void createPlanet(List<Shape> shapes, Vec3 center, double radius, 
                                    Color color, int numMoons, Random rand, String name) {
        Material planetMaterial = new Phong(color, 
            multiply(0.3, color), 35.0);
        shapes.add(new Sphere(center, radius, planetMaterial));
        
        // Monde
        for (int i = 0; i < numMoons; i++) {
            double moonDistance = radius * 3.5;
            double angle = (2 * Math.PI / numMoons) * i + rand.nextDouble() * 0.5;
            
            Vec3 moonPos = vec3(
                center.x() + Math.cos(angle) * moonDistance,
                center.y() + (rand.nextDouble() - 0.5) * radius * 0.5,
                center.z() + Math.sin(angle) * moonDistance
            );
            
            shapes.add(new Sphere(moonPos, radius * 0.2,
                new Phong(color(0.6, 0.6, 0.6), Color.white, 25.0)));
        }
    }
    
    private static void createEarth(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Erde - Blau mit grünen Akzenten
        Material earthMaterial = new Phong(
            color(0.1, 0.4, 0.8),   // Ozeanblau
            color(0.5, 0.6, 0.9),   // Spekular
            45.0
        );
        shapes.add(new Sphere(center, radius, earthMaterial));
        
        // Mond
        Vec3 moonPos = vec3(center.x() + radius * 4, center.y() + radius * 0.3, center.z() + radius * 0.5);
        shapes.add(new Sphere(moonPos, radius * 0.27,
            new Phong(color(0.75, 0.75, 0.75), color(0.5, 0.5, 0.5), 30.0)));
    }
    
    private static void createMars(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Mars - Rostrot
        Material marsMaterial = new Phong(
            color(0.8, 0.4, 0.2),
            color(0.6, 0.3, 0.1),
            25.0
        );
        shapes.add(new Sphere(center, radius, marsMaterial));
        
        // Phobos und Deimos
        Vec3 phobos = vec3(center.x() + radius * 2.5, center.y(), center.z() + radius * 0.5);
        shapes.add(new Sphere(phobos, radius * 0.15,
            new Phong(color(0.5, 0.5, 0.4), Color.white, 20.0)));
        
        Vec3 deimos = vec3(center.x() - radius * 3, center.y() + radius * 0.3, center.z() - radius);
        shapes.add(new Sphere(deimos, radius * 0.1,
            new Phong(color(0.55, 0.5, 0.45), Color.white, 20.0)));
    }
    
    private static void createAsteroidBelt(List<Shape> shapes, double innerRadius, 
                                          double outerRadius, int count, Random rand) {
        for (int i = 0; i < count; i++) {
            double angle = rand.nextDouble() * Math.PI * 2;
            double radius = innerRadius + rand.nextDouble() * (outerRadius - innerRadius);
            double height = (rand.nextDouble() - 0.5) * 3;
            
            Vec3 pos = vec3(
                Math.cos(angle) * radius,
                height,
                Math.sin(angle) * radius
            );
            
            double size = 0.05 + rand.nextDouble() * 0.15;
            Color asteroidColor = color(
                0.4 + rand.nextDouble() * 0.3,
                0.35 + rand.nextDouble() * 0.25,
                0.3 + rand.nextDouble() * 0.2
            );
            
            shapes.add(new Sphere(pos, size,
                new Phong(asteroidColor, multiply(0.2, asteroidColor), 15.0)));
        }
    }
    
    private static void createJupiter(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Jupiter - Orangebraun mit Bändern
        Material jupiterMaterial = new Phong(
            color(0.85, 0.7, 0.5),
            color(0.7, 0.6, 0.4),
            40.0
        );
        shapes.add(new Sphere(center, radius, jupiterMaterial));
        
        // Großer Roter Fleck (als kleine Kugel)
        Vec3 spotPos = vec3(center.x() + radius * 0.95, center.y() - radius * 0.3, center.z());
        shapes.add(new Sphere(spotPos, radius * 0.15,
            new Phong(color(0.9, 0.3, 0.2), color(0.8, 0.2, 0.1), 50.0)));
        
        // Galileische Monde
        String[] moonNames = {"Io", "Europa", "Ganymed", "Kallisto"};
        Color[] moonColors = {
            color(0.9, 0.8, 0.3),  // Io - schwefelgelb
            color(0.85, 0.85, 0.9), // Europa - eisig
            color(0.6, 0.55, 0.5),  // Ganymed
            color(0.5, 0.5, 0.55)   // Kallisto
        };
        
        for (int i = 0; i < 4; i++) {
            double moonDist = radius * (4 + i * 1.5);
            double angle = (Math.PI / 2) * i;
            
            Vec3 moonPos = vec3(
                center.x() + Math.cos(angle) * moonDist,
                center.y() + (rand.nextDouble() - 0.5) * radius * 0.5,
                center.z() + Math.sin(angle) * moonDist
            );
            
            shapes.add(new Sphere(moonPos, radius * (0.25 + i * 0.05),
                new Phong(moonColors[i], Color.white, 30.0)));
        }
    }
    
    private static void createSaturn(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Saturn - Blassgelb
        Material saturnMaterial = new Phong(
            color(0.95, 0.9, 0.7),
            color(0.9, 0.85, 0.65),
            45.0
        );
        shapes.add(new Sphere(center, radius, saturnMaterial));
        
        // Spektakuläre Ringe - mehrere Schichten
        createDetailedRings(shapes, center, radius * 1.5, radius * 3.0, 300, rand);
        
        // Titan
        Vec3 titanPos = vec3(center.x() + radius * 5.5, center.y() + radius * 0.2, center.z());
        shapes.add(new Sphere(titanPos, radius * 0.45,
            new Phong(color(0.8, 0.65, 0.5), Color.white, 35.0)));
    }
    
    private static void createUranus(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Uranus - Cyan/türkis
        Material uranusMaterial = new Phong(
            color(0.5, 0.8, 0.85),
            color(0.6, 0.9, 0.95),
            40.0
        );
        shapes.add(new Sphere(center, radius, uranusMaterial));
        
        // Dünne Ringe (vertikal geneigt)
        for (int i = 0; i < 80; i++) {
            double angle = rand.nextDouble() * Math.PI * 2;
            double ringRadius = radius * (2.0 + rand.nextDouble() * 0.8);
            
            Vec3 pos = vec3(
                center.x() + Math.cos(angle) * ringRadius * 0.3,
                center.y() + (rand.nextDouble() - 0.5) * 0.1,
                center.z() + Math.sin(angle) * ringRadius
            );
            
            shapes.add(new Sphere(pos, 0.03,
                new Phong(color(0.6, 0.7, 0.75), Color.white, 20.0)));
        }
        
        // Monde
        for (int i = 0; i < 5; i++) {
            double moonDist = radius * (3 + i * 0.8);
            double angle = (2 * Math.PI / 5) * i;
            
            Vec3 moonPos = vec3(
                center.x() + Math.cos(angle) * moonDist,
                center.y() + (rand.nextDouble() - 0.5) * radius * 0.4,
                center.z() + Math.sin(angle) * moonDist
            );
            
            shapes.add(new Sphere(moonPos, radius * 0.15,
                new Phong(color(0.65, 0.65, 0.7), Color.white, 25.0)));
        }
    }
    
    private static void createNeptune(List<Shape> shapes, Vec3 center, double radius, Random rand) {
        // Neptun - Tiefblau
        Material neptuneMaterial = new Phong(
            color(0.2, 0.4, 0.9),
            color(0.3, 0.5, 1.0),
            50.0
        );
        shapes.add(new Sphere(center, radius, neptuneMaterial));
        
        // Großer dunkler Fleck
        Vec3 stormPos = vec3(center.x() + radius * 0.9, center.y() + radius * 0.2, center.z());
        shapes.add(new Sphere(stormPos, radius * 0.12,
            new Phong(color(0.15, 0.25, 0.7), color(0.1, 0.2, 0.6), 40.0)));
        
        // Triton (großer Mond)
        Vec3 tritonPos = vec3(center.x() - radius * 4, center.y() - radius * 0.5, center.z() + radius * 2);
        shapes.add(new Sphere(tritonPos, radius * 0.35,
            new Phong(color(0.8, 0.75, 0.7), Color.white, 30.0)));
    }
    
    private static void createDetailedRings(List<Shape> shapes, Vec3 center, 
                                           double innerRadius, double outerRadius, 
                                           int numParticles, Random rand) {
        for (int i = 0; i < numParticles; i++) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double radius = innerRadius + rand.nextDouble() * (outerRadius - innerRadius);
            
            // Mehrere Ring-Ebenen für 3D-Effekt
            Vec3 pos = vec3(
                center.x() + Math.cos(angle) * radius,
                center.y() + (rand.nextDouble() - 0.5) * 0.15,
                center.z() + Math.sin(angle) * radius
            );
            
            double particleSize = 0.04 + rand.nextDouble() * 0.08;
            
            // Farbvariationen im Ring
            double distFactor = (radius - innerRadius) / (outerRadius - innerRadius);
            Color ringColor = color(
                0.75 + distFactor * 0.2,
                0.7 + distFactor * 0.15,
                0.6 + distFactor * 0.2
            );
            
            shapes.add(new Sphere(pos, particleSize,
                new Phong(ringColor, multiply(0.5, ringColor), 25.0)));
        }
    }
}