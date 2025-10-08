
package cgg;

import tools.*;

public class Image implements tools.Image {

    // ---8<--- missing-implementation
    // Provides storage for the image data.
    public Image(int width, int height) {
    }

    // Stores the RGB color components for one pixel addressed
    // by it's coordinates in the image.
    public void setPixel(int x, int y, Color color) {
    }

    // Stores the image data in a PNG file.
    public void writePng(String name) {
        System.out.format("Implement function `cgg.Image.writePng` to actually write image `%s`\n", name);
        // ImageWriter.writePng(name, data, width, height);
    }
    // --->8---

    // Retrieves the RGB color components for one particular pixel addressed
    // by it's coordinates in the image.
    public Color getPixel(int x, int y) {
        return Color.black;
    }

    public void writeHdr(String name) {
        System.out.format("Implement function `cgg.Image.writeHdr` to actually write image `%s`\n", name);
        // ImageWriter.writeHdr(name, data, width, height);
    }

    public int width() {
        // This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual width of the Image is
        // returned.
        return 0;
    }

    public int height() {
        // This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual height of the Image is
        // returned.
        return 0;
    }
}
