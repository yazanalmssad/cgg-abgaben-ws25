package cgg;
 
import java.util.ArrayList;
import java.util.List;
import tools.*;

public class Circles implements Sampler{
    private final List<Circle> circles;
 
    public Circles(int count, int imageWidth, int imageHeight){
        this.circles = new ArrayList<>();
        genCircles(count, imageWidth, imageHeight);
    }
    private void genCircles(int count, int width, int height){
        int gridSize = (int) Math.ceil(Math.sqrt(count));
        double cellWidth = (double) width / gridSize;
        double cellHeight = (double) height / gridSize;

        for (int i = 0; i < count; i++) {
            int row = i / gridSize;
            int col = i % gridSize;

            double x = (col + 0.5) * cellWidth;
            double y = (row + 0.5) * cellHeight;

            double radius = 10 + (i * 15);

            Color color = new Color((double) col / gridSize, (double) row / gridSize, 0.8);
            
            circles.add(new Circle(new Vec2(x, y), radius, color));
        }
        circles.sort((c1, c2) -> Double.compare(c1.getRadius(), c2.getRadius()));
    }
    @Override
    public Color getColor(Vec2 point){
        for (Circle circle : circles) {
            if (circle.coversPoint(point)) {
                return circle.getColor();
            }
        }
        return Color.black;
    }
    
}
