package uk.ac.cam.cl.gfxintro.zc344.tick1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BumpySphere extends Sphere {
    private float BUMP_FACTOR = 5f;
    private float[][] heightMap;
    private int bumpMapHeight;
    private int bumpMapWidth;

    public BumpySphere(Vector3 position,
                       double radius,
                       ColorRGB colour,
                       String bumpMapImg) {
        super(position, radius, colour);
        try {
            BufferedImage inputImg = ImageIO.read(new File(bumpMapImg));
            bumpMapHeight = inputImg.getHeight();
            bumpMapWidth = inputImg.getWidth();
            heightMap = new float[bumpMapHeight][bumpMapWidth];
            for (int row = 0; row < bumpMapHeight; row++) {
                for (int col = 0; col < bumpMapWidth; col++) {
                    float height =
                        (float)(inputImg.getRGB(col, row) & 0xFF) / 0xFF;
                    heightMap[row][col] = BUMP_FACTOR * height;
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating bump map");
            e.printStackTrace();
        }
    }

    // Get normal to surface at position
    @Override
    public Vector3 getNormalAt(Vector3 position) {
        // TODO: return the normal modified by the bump map
        return new Vector3(0);
    }
}
