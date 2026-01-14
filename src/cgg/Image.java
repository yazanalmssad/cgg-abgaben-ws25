
package cgg;

import tools.*;

public class Image implements tools.Image {
    protected int width;
    protected int height;
    protected double[] data;

    // ---8<--- missing-implementation
    // Provides storage for the image data.
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        data = new double[width * height * 3]; 
    }

    // Stores the RGB color components for one pixel addressed
    // by it's coordinates in the image.
    @Override
    public void setPixel(int x, int y, Color color) {
        int index = (y * width + x) * 3;
        data[index] = color.r();
        data[index + 1] = color.g();
        data[index + 2] = color.b();
    }
 
    // Stores the image data in a PNG file.
    public void writePng(String name) {
        //System.out.format("Implement function `cgg.Image.writePng` to actually write image `%s`\n", name);
        ImageWriter.writePng(name, data, width, height);
    }
    // --->8---

    // Retrieves the RGB color components for one particular pixel addressed
    // by it's coordinates in the image.
    @Override
    public Color getPixel(int x, int y) {
        int index = (y * width + x) * 3;
        return new Color(data[index], data[index + 1], data[index + 2]);
    }
 
    public void writeHdr(String name) {
        //System.out.format("Implement function `cgg.Image.writeHdr` to actually write image `%s`\n", name);
        ImageWriter.writeHdr(name, data, width, height);
    }

    public void sample(Sampler sampler) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x, y, sampler.getColor(new Vec2(x, y)));
            }
        }
    }
 
    @Override
    public int width() {
        // This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual width of the Image is
        // returned.
        return width;
    }

    @Override
    public int height() {
        // This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual height of the Image is
        // returned.
        return height;
    }
}
