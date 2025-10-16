
package cgg;
 
import static tools.Functions.*;
 
public class A01 {
 
  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    var circles = new Circles(9, width, height);

    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x, y, circles.getColor(vec2(x, y)));

    // Write the image to disk.
    image.writePng("images/a01.png");
  }
}
