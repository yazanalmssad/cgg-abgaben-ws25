package cgg;

import tools.*;
  
public class Circle {
    private final Vec2 position;
    private final double radius;
    private final Color color; 

    public Circle(Vec2 position, double radius, Color color) {
        this.position = position;
        this.radius = radius;
        this.color = color;
    }

    public boolean coversPoint(Vec2 point) {
        double dx = point.x() - position.x();
        double dy = point.y() - position.y();
        return dx * dx + dy * dy <= radius * radius;
    }

    public double getRadius(){
        return radius;
    }
    public Color getColor(){
        return color;
    }
    public Vec2 getPosition(){
        return position;
    }
}
