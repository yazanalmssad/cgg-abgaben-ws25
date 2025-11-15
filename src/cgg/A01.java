
package cgg;
 
import static tools.Functions.*;
 
public class A01 {
 
  public static void main(String[] args) {
    int width = 400;
    int height = 400;
    var circles = new Circles(9, width, height);
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
       
        image.setPixel(x, y, circles.getColor(vec2(x, y)));

    image.writePng("a01");
  }
}
