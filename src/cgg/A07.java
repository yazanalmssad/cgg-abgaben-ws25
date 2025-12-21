package cgg;

import java.util.Arrays;
import java.util.List;
import tools.*;
import static tools.Functions.*;

public class A07 {
    public static void main(String[] args) {
        int width = 1920;  // Höhere Auflösung für besseres Bild
        int height = 1080;

        // Spektakuläre Sonnensystem-Szene
        List<Shape> shapes = SceneGenerator.createSolarSystemScene();

        // OPTIMIERTE KAMERA: Dramatische Perspektive
        SimpleCamera camera = new SimpleCamera(
            Math.toRadians(50),      // Field of View
            width,
            height,
            vec3(40, 15, 35),        // Kamera: Erhöht und schräg für bessere Übersicht
            vec3(20, 0, 0)           // Blick auf die inneren Planeten (nicht direkt auf Sonne)
        );

        // VERBESSERTE BELEUCHTUNG
        // Hauptlicht von der Sonne (warm und stark)
        LightSource sunLight = new DirectionalLight(
            normalize(vec3(-1, -0.3, -0.3)),  // Von links-oben-vorn
            color(2.5, 2.3, 2.0)              // Warmes, helles Sonnenlicht
        );
        
        // Fülllicht (simuliert reflektiertes Licht im Weltraum)
        LightSource fillLight = new DirectionalLight(
            normalize(vec3(0.5, -0.5, 0.7)),  // Von rechts-oben
            color(0.4, 0.5, 0.6)              // Kühles, schwaches Licht
        );
        
        // Rimlight (für Silhouetten)
        LightSource rimLight = new DirectionalLight(
            normalize(vec3(0, 0.3, -1)),      // Von hinten
            color(0.3, 0.35, 0.5)             // Subtiles blaues Randlicht
        );
        
        // Umgebungslicht (sehr schwach für Weltraum)
        LightSource ambient = new DirectionalLight(
            normalize(vec3(0, -1, 0)),        
            color(0.05, 0.05, 0.08)           // Sehr dunkles Umgebungslicht
        );
        
        List<LightSource> lights = Arrays.asList(sunLight, fillLight, rimLight, ambient);

        // RayTracer erstellen
        RayTracer raytracer = new RayTracer(camera, shapes, lights);

        // Bild erstellen
        Image image = new Image(width, height);

        // PARALLELES RENDERING
        System.out.println("Starte paralleles Rendering...");
        System.out.println("Auflösung: " + width + "x" + height);
        System.out.println("Anzahl Objekte: " + shapes.size());
        
        long startTime = System.currentTimeMillis();
        
        Color[][] colors = raytracer.renderParallel(width, height);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Rendering abgeschlossen in " + (endTime - startTime) + " ms");
        
        // Farben in Bild übertragen
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setPixel(x, y, colors[y][x]);
            }
            
            // Fortschrittsanzeige
            if (y % 100 == 0) {
                System.out.println("Übertrage Pixel... " + (y * 100 / height) + "%");
            }
        }

        // Bild speichern
        
        image.writePng("a07");
        
}}